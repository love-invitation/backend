package jun.invitation.api;

import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.config.jwt.JwtProperties;
import jun.invitation.config.jwt.RefreshToken;
import jun.invitation.config.jwt.TokenService;
import jun.invitation.domain.User;
import jun.invitation.dto.LoginRequestDto;
import jun.invitation.dto.ResponseDto;
import jun.invitation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginRequest(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        String username = loginRequestDto.getProvider() + "_" + loginRequestDto.getProviderId();
        User userByUsername = userService.findUserByUsername(username);

        if (userByUsername == null) {
            User newUser = User.builder()
                    .username(username)
                    .email(loginRequestDto.getEmail())
                    .role("ROLE_USER")
                    .provider(loginRequestDto.getProvider())
                    .providerId(loginRequestDto.getProviderId())
                    .build();

            userByUsername = userService.saveUser(newUser);
        }

        String accessToken = tokenService.generateAccessToken(userByUsername);

        // refresh token 생성
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), userByUsername.getId());

        // redis에 저장
        tokenService.saveRefreshToken(refreshToken);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", refreshToken.getRefreshToken())
                .httpOnly(true)
                .maxAge(Duration.ofDays(7))
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(
                        ResponseDto.builder()
                                .status(HttpServletResponse.SC_OK)
                                .message("토큰 발급 성공")
                                .result(JwtProperties.TOKEN_PREFIX+accessToken)
                                .build()
                );
    }
}
