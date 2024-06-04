package jun.invitation.domain.gallery.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.QGallery;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jun.invitation.domain.gallery.QGallery.*;

@RequiredArgsConstructor
@Slf4j
public class GalleryRepositoryCustomImpl implements GalleryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByGalleries(List<Gallery> galleries) {
        queryFactory.delete(gallery)
                .where(gallery.in(galleries))
                .execute();

        em.flush();
        em.clear();
    }
}
