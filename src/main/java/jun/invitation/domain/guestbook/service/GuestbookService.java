package jun.invitation.domain.guestbook.service;

import jun.invitation.domain.guestbook.dao.GuestbookRepository;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;
    private final InvitationService invitationService;

    public void requestCreate(GuestbookDto guestbookDto, Long invitationId) {

        Invitation invitation = invitationService.findInvitation(invitationId);

        Guestbook guestbook = new Guestbook(
                guestbookDto.getName(),
                guestbookDto.getPassword(),
                guestbookDto.getMessage(),
                invitation
        );

        guestbookRepository.save(guestbook);
    }

}
