package jun.invitation.auth.jwt.refreshToken.dao;

import jun.invitation.auth.jwt.refreshToken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}
