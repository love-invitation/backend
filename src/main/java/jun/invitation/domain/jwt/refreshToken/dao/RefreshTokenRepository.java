package jun.invitation.domain.jwt.refreshToken.dao;

import jun.invitation.domain.jwt.refreshToken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}