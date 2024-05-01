package jun.invitation.domain.transport.dao;

import jun.invitation.domain.transport.domain.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TransportRepository extends JpaRepository<Transport, Long> {
}
