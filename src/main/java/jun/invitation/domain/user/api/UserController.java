package jun.invitation.domain.user.api;

import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.auth.jwt.JwtProperties;
import jun.invitation.auth.jwt.refreshToken.domain.RefreshToken;
import jun.invitation.auth.jwt.service.TokenService;
import jun.invitation.domain.user.domain.User;
import jun.invitation.dto.LoginRequestDto;
import jun.invitation.dto.ResponseDto;
import jun.invitation.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

@RestController
@RequiredArgsConstructor @Slf4j
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

        ResponseCookie responseCookie = createCookie("refreshToken", refreshToken.getRefreshToken(), 7);

        log.info("jwtToken ={}",JwtProperties.TOKEN_PREFIX+accessToken);
        ResponseCookie accessCookie = createCookie("Authorization", URLEncoder.encode(JwtProperties.TOKEN_PREFIX + accessToken, StandardCharsets.UTF_8).replaceAll("\\+", "%20"), 1);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString(),accessCookie.toString() )
                .body(
                        ResponseDto.builder()
                                .status(HttpServletResponse.SC_OK)
                                .message("토큰 발급 성공")
                                .build()
                );
    }

    private static ResponseCookie createCookie(String name, String value, int days) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .maxAge(Duration.ofDays(days))
                .secure(true)
                .build();
    }
}
