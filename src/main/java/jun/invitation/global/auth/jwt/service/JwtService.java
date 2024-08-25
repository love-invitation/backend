package jun.invitation.global.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jun.invitation.global.auth.jwt.exception.NoTokenException;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static jun.invitation.global.auth.jwt.JwtProperties.*;

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

        if (cookies == null) {
            return null;
        }

        try {
            Cookie cookie = findJWTCookie(cookies);
            return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8)
                    .replaceAll("%20", " ");
        } catch (NoTokenException e) {
            return null;
        }

    }

    private Cookie findJWTCookie(Cookie[] cookies) {

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(HEADER_STRING))
                .findFirst().orElseThrow(NoTokenException::new);
    }

    public Cookie logout(Cookie[] cookies) {

        Cookie target = findJWTCookie(cookies);
        target.setMaxAge(0);

        return target;
    }
}
