package jun.invitation.domain.invitation.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.image.domain.QImage;
import lombok.RequiredArgsConstructor;

import static jun.invitation.domain.gallery.QGallery.*;
import static jun.invitation.domain.invitation.domain.QInvitation.*;
import static jun.invitation.domain.productInfo.domain.QProductInfo.*;
import static jun.invitation.domain.shareThumbnail.domain.QShareThumbnail.*;
import static jun.invitation.image.domain.QImage.*;

@RequiredArgsConstructor
public class InvitationRepositoryImpl implements CustomInvitationRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Invitation findByIdWithALL(Long productId) {

        return queryFactory
                .selectFrom(invitation)
                .join(invitation.shareThumbnail, shareThumbnail).fetchJoin()
                .join(invitation.shareThumbnail.image, new QImage("shareThumbnailImage")).fetchJoin()
                .join(invitation.mainImage, new QImage("mainImage")).fetchJoin()
                .join(invitation.productInfo, productInfo).fetchJoin()
                .join(invitation.gallery, gallery).fetchJoin()
                .join(gallery.image, image).fetchJoin()
                .where(invitation.id.eq(productId))
                .fetchFirst();
    }
}