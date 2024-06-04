package jun.invitation.domain.transport.dao;

import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.transport.domain.Transport;

import java.util.List;

public interface TransportRepositoryCustom {
    void deleteByTransports(List<Transport> transports);
}
