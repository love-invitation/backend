package jun.invitation.domain.invitation.dao;

import jun.invitation.domain.invitation.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    Optional<Invitation> findByTsid(Long tsid);
}