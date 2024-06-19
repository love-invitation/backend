package jun.invitation.domain.transport.dao;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.transport.domain.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransportRepository extends JpaRepository<Transport, Long>{
    @Modifying
    @Query(value = "delete from Transport t where t in :transports")
    void deleteByTransports(@Param(value = "transports") List<Transport> transports);

    @Modifying
    @Query(value = "delete from Transport t where t.invitation.id = :id")
    void deleteByInvitationId(@Param(value = "id") Long invitationId);
}
