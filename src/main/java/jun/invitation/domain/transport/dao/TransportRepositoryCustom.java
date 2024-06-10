package jun.invitation.domain.transport.dao;

import jun.invitation.domain.transport.domain.Transport;

import java.util.List;

public interface TransportRepositoryCustom {
    void deleteByInvitationId(Long invitationId);
    void deleteByTransports(List<Transport> transports);
}
