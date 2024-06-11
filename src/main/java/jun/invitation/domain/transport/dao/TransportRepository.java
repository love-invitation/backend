package jun.invitation.domain.transport.dao;

import jun.invitation.domain.transport.domain.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<Transport, Long>, TransportRepositoryCustom {
}
