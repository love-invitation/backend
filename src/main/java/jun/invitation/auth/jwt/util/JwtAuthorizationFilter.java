package jun.invitation.auth.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jun.invitation.auth.PrincipalDetails;
import jun.invitation.auth.jwt.JwtProperties;
import jun.invitation.domain.user.domain.User;
import jun.invitation.domain.user.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String cookie = extractToken(request);

        if (cookie == null || !cookie.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = cookie.replace(JwtProperties.TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        if (username != null) {
            User user = userRepository.findByUsername(username);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    public String extractToken(HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JwtProperties.HEADER_STRING.equals(cookie.getName())) {
                    String header = cookie.getValue();
                    return URLDecoder.decode(header, StandardCharsets.UTF_8.name())
                            .replaceAll("%20", " ");
                }
            }
        }
        return null;
    }
}

