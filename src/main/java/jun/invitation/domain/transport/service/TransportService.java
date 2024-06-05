package jun.invitation.domain.transport.service;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.dto.TransportDto;
import jun.invitation.domain.transport.dao.TransportRepository;
import jun.invitation.domain.transport.domain.Transport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;

    public void deleteByTransports(Long invitationId) {
        transportRepository.deleteByInvitationId(invitationId);
    }

    public void save(List<TransportDto> transportDtos, Invitation invitation) {
        transportDtos.forEach(
                transportDto -> new Transport(transportDto)
                .register(invitation)
        );
    }
}
