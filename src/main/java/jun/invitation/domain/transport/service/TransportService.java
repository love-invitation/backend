package jun.invitation.domain.transport.service;

import jun.invitation.domain.transport.dao.TransportRepository;
import jun.invitation.domain.transport.domain.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional @RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;

    public void addTransport(Transport transport) {
        transportRepository.save(transport);
    }

}
