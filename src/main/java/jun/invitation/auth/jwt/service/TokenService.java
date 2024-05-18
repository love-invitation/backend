package jun.invitation.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jun.invitation.auth.jwt.JwtProperties;
import jun.invitation.auth.refreshToken.dao.RefreshTokenRepository;
import jun.invitation.auth.refreshToken.domain.RefreshToken;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(User user) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

    }

    public Optional<RefreshToken> findRefreshToken(String refreshTkn) {
        return refreshTokenRepository.findById(refreshTkn);
    }

    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    public String extractToken(Cookie[] cookies) throws UnsupportedEncodingException {

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
