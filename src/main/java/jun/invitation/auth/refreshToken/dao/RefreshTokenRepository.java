package jun.invitation.auth.refreshToken.dao;

import jun.invitation.auth.refreshToken.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
}
