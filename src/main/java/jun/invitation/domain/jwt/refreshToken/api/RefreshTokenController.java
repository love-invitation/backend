package jun.invitation.domain.jwt.refreshToken.api;

import jun.invitation.domain.jwt.JwtProperties;
import jun.invitation.domain.jwt.refreshToken.domain.RefreshToken;
import jun.invitation.domain.jwt.service.TokenService;
import jun.invitation.domain.user.domain.User;
import jun.invitation.dto.ResponseDto;
import jun.invitation.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenController {

    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/api/token/refresh")
    public ResponseEntity<ResponseDto> regenerateAccessTkn(@RequestHeader("refresh") String refreshTkn) {

        log.info("received Refresh token={}", refreshTkn);
        Optional<RefreshToken> refreshToken = tokenService.findRefreshToken(refreshTkn);


        if (refreshToken.isPresent()) {
            User user = userService.findUser(refreshToken.get().getUserId()).orElseGet(User::new);
            String newAccessToken = tokenService.generateAccessToken(user);
            log.info("new token = {}", newAccessToken);

            return ResponseEntity.ok(
                    ResponseDto.builder()
                            .status(SC_OK)
                            .message("Regenerate Complete.")
                            .result(JwtProperties.TOKEN_PREFIX+newAccessToken)
                            .build()
            );
        }

        return ResponseEntity.status(SC_NOT_FOUND)
                .body(
                        ResponseDto.builder()
                                .status(SC_NOT_FOUND)
                                .message("Refresh Token has expired.")
                                .build());
    }
}
