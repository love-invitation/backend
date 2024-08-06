package jun.invitation.global.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.global.auth.jwt.JwtProperties;
import jun.invitation.global.auth.jwt.exception.InvalidTokenException;
import jun.invitation.global.auth.jwt.exception.NoTokenException;
import jun.invitation.global.auth.jwt.service.JwtService;
import jun.invitation.global.auth.oauth.PrincipalDetails;
import jun.invitation.domain.user.dao.UserRepository;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String cookie = jwtService.extractToken(request.getCookies());

        if (ObjectUtils.isEmpty(cookie)) {
            request.setAttribute("exception", new NoTokenException());
            chain.doFilter(request, response);
            return;
        }

        if (!cookie.startsWith(JwtProperties.TOKEN_PREFIX)) {
            request.setAttribute("exception", new InvalidTokenException());
            chain.doFilter(request, response);
            return;
        }

        String token = cookie.replace(JwtProperties.TOKEN_PREFIX, "");


        String username = null;
        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("username").asString();
        } catch (Exception e) {
            log.info(e.getMessage());
            request.setAttribute("exception", e);
        }

        Optional.ofNullable(username)
                        .ifPresent(this::login);

        chain.doFilter(request, response);
    }

    private void login(String username) {
        User user = userRepository.findByUsername(username);

        PrincipalDetails principalDetails = new PrincipalDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principalDetails,
                null,
                principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

