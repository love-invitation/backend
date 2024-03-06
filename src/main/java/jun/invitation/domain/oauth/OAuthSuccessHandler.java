package jun.invitation.domain.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.domain.auth.PrincipalDetails;
import jun.invitation.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

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

        response.getWriter().write(objectMapper.writeValueAsString(
                ResponseDto.builder()
                        .status(HttpServletResponse.SC_OK)
                        .message("login success")
                        .result("jwtToken")
                        .build()
        ));

        }
}