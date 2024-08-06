package jun.invitation.domain.transport.service;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.transport.dao.TransportRepository;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.transport.dto.TransportDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransportService {

    private final TransportRepository transportRepository;

    public void delete(Long invitationId) {
        transportRepository.deleteByProductId(invitationId);
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
    public void update(Invitation invitation, List<TransportDto> newTransportDtos) {

        List<Transport> currentTransports = invitation.getTransport();

        if (!ObjectUtils.isEmpty(currentTransports)){
            transportRepository.deleteByTransports(currentTransports);
            invitation.getTransport().clear();
        }

        if (!ObjectUtils.isEmpty(newTransportDtos)) {
            newTransportDtos.forEach(transportDto -> {
                Transport transport = new Transport(transportDto);
                transport.register(invitation);
            });
        }
    }

    public void create(List<TransportDto> transport, Invitation invitation) {
        if (!ObjectUtils.isEmpty(transport))
            save(transport, invitation);
    }
}
