package jun.invitation.config.oauth.handler;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.config.auth.PrincipalDetails;
import jun.invitation.config.jwt.JwtProperties;
import jun.invitation.config.jwt.RefreshToken;
import jun.invitation.config.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
        log.info("OAuthSuccessHandler : 호출");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        log.info("{}", principalDetails.getUser().getId());
        log.info("{}", principalDetails.getUser().getUsername());

        // access token 생성
        String jwtToken = tokenService.generateAccessToken(principalDetails.getUser());

        // refresh token 생성
        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID().toString(), principalDetails.getUser().getId());

        // redis에 저장
        tokenService.saveRefreshToken(refreshToken);

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
        response.addHeader("Refresh", refreshToken.getRefreshToken()); // cookie에 담기

    }
}
