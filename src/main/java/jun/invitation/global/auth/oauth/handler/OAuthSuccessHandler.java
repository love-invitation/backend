package jun.invitation.global.auth.oauth.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.global.auth.jwt.service.JwtService;
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

import static jun.invitation.global.auth.jwt.JwtProperties.HEADER_STRING;
import static jun.invitation.global.auth.jwt.JwtProperties.TOKEN_PREFIX;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User currentUser = SecurityUtils.getCurrentUser();

        log.info(currentUser.getEmail());

        String accessToken = jwtService.generateAccessToken(currentUser);

        ResponseCookie accessCookie = createCookie(HEADER_STRING,
                URLEncoder.encode(TOKEN_PREFIX + accessToken, StandardCharsets.UTF_8).replaceAll("\\+", "%20"), 1);

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());

        response.sendRedirect("https://dev.pinkcotton.shop:3000");
    }

    private static ResponseCookie createCookie(String name, String value, int days) {
        return ResponseCookie.from(name, value)
                .maxAge(Duration.ofDays(days))
                .sameSite("None")
                .domain(".pinkcotton.shop")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .build();
    }
}