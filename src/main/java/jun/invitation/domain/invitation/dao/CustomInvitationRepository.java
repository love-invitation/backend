package jun.invitation.domain.invitation.dao;

import jun.invitation.domain.invitation.domain.Invitation;

public interface CustomInvitationRepository {
    Invitation findByIdWithALL(Long productId);
}
