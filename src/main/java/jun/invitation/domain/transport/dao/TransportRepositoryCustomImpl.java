package jun.invitation.domain.transport.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.transport.domain.Transport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static jun.invitation.domain.transport.domain.QTransport.transport;


@RequiredArgsConstructor
@Slf4j
public class TransportRepositoryCustomImpl implements TransportRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteByInvitationId(Long invitationId) {
        queryFactory.delete(transport)
                .where(transport.invitation.id.eq(invitationId))
                .execute();
    }

    @Override
    public void deleteByTransports(List<Transport> transports) {
        queryFactory.delete(transport)
                .where(transport.in(transports))
                .execute();
        em.flush();
    }
}
