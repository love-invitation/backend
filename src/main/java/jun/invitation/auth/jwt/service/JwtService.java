package jun.invitation.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jun.invitation.auth.jwt.exception.NoTokenException;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

import static jun.invitation.auth.jwt.JwtProperties.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {


    public String generateAccessToken(User user) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512(SECRET));

    }

    public String extractToken(Cookie[] cookies) throws UnsupportedEncodingException {

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (HEADER_STRING.equals(cookie.getName())) {
                    String header = cookie.getValue();
                    return URLDecoder.decode(header, StandardCharsets.UTF_8.name())
                            .replaceAll("%20", " ");
                }
            }
        }
        return null;
    }

    public Cookie logout(Cookie[] cookies) {

        Cookie target = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(HEADER_STRING))
                .findFirst()
                .orElseThrow(NoTokenException::new);

        target.setMaxAge(0);
        return target;
    }
}
