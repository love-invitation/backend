package jun.invitation.domain.user.dao;

import jun.invitation.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByProviderAndProviderId(String provider, String providerId);

    User findByUsername(String username);

}
