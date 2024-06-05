package jun.invitation.domain.gallery.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jun.invitation.domain.gallery.QGallery.*;

@RequiredArgsConstructor
@Slf4j
public class GalleryRepositoryCustomImpl implements GalleryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByInvitationId(Long invitationId) {
        queryFactory.delete(gallery)
                .where(gallery.invitation.id.in(invitationId))
                .execute();
    }
}
