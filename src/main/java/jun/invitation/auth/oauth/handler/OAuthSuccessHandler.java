package jun.invitation.auth.oauth.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.auth.jwt.JwtProperties;
import jun.invitation.auth.jwt.service.TokenService;
import jun.invitation.auth.refreshToken.domain.RefreshToken;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User currentUser = SecurityUtils.getCurrentUser();

        log.info(currentUser.toString());

        String accessToken = tokenService.generateAccessToken(currentUser);

        // refresh token 생성
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), currentUser.getId());

        // redis에 저장
        tokenService.saveRefreshToken(refreshToken);

        ResponseCookie responseCookie = createCookie("refreshToken", refreshToken.getRefreshToken(), 7);
        ResponseCookie accessCookie = createCookie("Authorization",
                URLEncoder.encode(JwtProperties.TOKEN_PREFIX + accessToken, StandardCharsets.UTF_8).replaceAll("\\+", "%20"), 1);

        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());


        response.sendRedirect("https://dev.pinkcotton.shop:3000");
    }

    // TODO: HTTPS 설정하고 secure(true) 주석 풀어주기
    private static ResponseCookie createCookie(String name, String value, int days) {
        return ResponseCookie.from(name, value)
                .maxAge(Duration.ofDays(days))
                .sameSite("None")
                .domain(".pinkcotton.shop")
                .path("/")
                .secure(true)
                .build();
    }
}