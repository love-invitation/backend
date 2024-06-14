package jun.invitation.domain.transport.service;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.transport.dto.TransportDto;
import jun.invitation.domain.transport.dao.TransportRepository;
import jun.invitation.domain.transport.domain.Transport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransportService {

    private final TransportRepository transportRepository;

    public void delete(Long invitationId) {
        transportRepository.deleteByInvitationId(invitationId);
    }

    public void save(List<TransportDto> transportDtos, Invitation invitation) {
        transportDtos.forEach(
                transportDto -> new Transport(transportDto)
                .register(invitation)
        );
    }

    /**
     *      -> 기존 currentTransports 있으면 delete.
     *      -> 수정할 newTransports가 있다면 save.
     */
    public void update(List<Transport> currentTransports, Invitation invitation, List<TransportDto> newTransportDtos) {

        if (!currentTransports.isEmpty())
            transportRepository.deleteByTransports(currentTransports);

        if (newTransportDtos != null && !newTransportDtos.isEmpty()) {
            newTransportDtos.stream()
                    .map(transportDto -> {
                        Transport transport = new Transport(transportDto);
                        transport.register(invitation);
                        return transport;
                    })
                    .collect(Collectors.toList());
        }
    }
}
