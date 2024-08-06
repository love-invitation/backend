package jun.invitation.domain.invitation.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jun.invitation.domain.image.domain.QImage;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static jun.invitation.domain.gallery.QGallery.*;
import static jun.invitation.domain.image.domain.QImage.image;
import static jun.invitation.domain.invitation.domain.QInvitation.*;
import static jun.invitation.domain.productInfo.domain.QProductInfo.productInfo;
import static jun.invitation.domain.reservation.domain.QReservation.reservation;
import static jun.invitation.domain.shareThumbnail.domain.QShareThumbnail.*;

@RequiredArgsConstructor
public class InvitationRepositoryImpl implements CustomInvitationRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Invitation> findByTsidIdWithALL(Long tsid) {

        return Optional.ofNullable(queryFactory
                .selectFrom(invitation)
                .leftJoin(invitation.shareThumbnail, shareThumbnail).fetchJoin()
                .leftJoin(invitation.shareThumbnail.image, new QImage("shareThumbnailImage")).fetchJoin()
                .leftJoin(invitation.mainImage, new QImage("mainImage")).fetchJoin()
                .join(invitation.productInfo, productInfo).fetchJoin()
                .join(invitation.gallery, gallery).fetchJoin()
                .leftJoin(invitation.reservation, reservation).fetchJoin()
                .join(gallery.image, image).fetchJoin()
                .where(invitation.tsid.eq(tsid))
                .fetchFirst());
    }
}