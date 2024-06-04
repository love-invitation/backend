package jun.invitation.domain.product.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;

import static jun.invitation.domain.product.domain.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByInvitation(Invitation invitation) {
        queryFactory.delete(product)
                .where(product.id.eq(invitation.getId()))
                .execute();
    }
}
