package jun.invitation.domain.guestbook.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.domain.QGuestbook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static jun.invitation.domain.guestbook.domain.QGuestbook.*;


@RequiredArgsConstructor
@Slf4j
public class GuestbookRepositoryCustomImpl implements GuestbookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByGuestbooks(List<Guestbook> guestbooks) {

        queryFactory.delete(guestbook)
                .where(guestbook.in(guestbooks))
                .execute();

        em.flush();
        em.clear();
    }
}
