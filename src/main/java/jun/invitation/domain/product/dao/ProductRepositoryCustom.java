package jun.invitation.domain.product.dao;

import jun.invitation.domain.invitation.domain.Invitation;

public interface ProductRepositoryCustom {
    void deleteByInvitation(Invitation invitation);
}
