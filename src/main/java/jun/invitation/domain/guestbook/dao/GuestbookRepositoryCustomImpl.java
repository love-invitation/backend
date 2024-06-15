package jun.invitation.domain.guestbook.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static jun.invitation.domain.guestbook.domain.QGuestbook.guestbook;


//@RequiredArgsConstructor
//@Slf4j
//public class GuestbookRepositoryCustomImpl implements GuestbookRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public void deleteByInvitationId(Long invitationId) {
//
//        queryFactory.delete(guestbook)
//                .where(guestbook.invitation.id.in(invitationId))
//                .execute();
//    }
//}
