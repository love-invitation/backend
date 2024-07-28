package jun.invitation.domain.invitation.dao;

import jun.invitation.domain.invitation.domain.Invitation;

import java.util.Optional;

public interface CustomInvitationRepository {
    Optional<Invitation> findByTsidIdWithALL(Long tsid);
}
