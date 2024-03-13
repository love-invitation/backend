package jun.invitation.auth.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jun.invitation.auth.jwt.JwtProperties;
import jun.invitation.auth.jwt.refreshToken.dao.RefreshTokenRepository;
import jun.invitation.auth.jwt.refreshToken.domain.RefreshToken;
import jun.invitation.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
}
