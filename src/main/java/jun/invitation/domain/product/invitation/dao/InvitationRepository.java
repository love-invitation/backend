package jun.invitation.domain.product.invitation.dao;

import jun.invitation.domain.product.invitation.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
