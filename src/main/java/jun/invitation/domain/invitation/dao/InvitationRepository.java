package jun.invitation.domain.invitation.dao;

import jun.invitation.domain.invitation.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}