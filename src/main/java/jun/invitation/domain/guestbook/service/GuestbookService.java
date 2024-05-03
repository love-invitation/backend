package jun.invitation.domain.guestbook.service;

import jun.invitation.domain.guestbook.dao.GuestbookRepository;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public void requestCreate(GuestbookDto guestbookDto, Invitation invitation) {

        Guestbook guestbook = new Guestbook(
                guestbookDto.getName(),
                guestbookDto.getPassword(),
                guestbookDto.getMessage(),
                invitation
        );

        guestbookRepository.save(guestbook);
    }

    public List<GuestbookResponseDto> getResponseDtoList(Invitation invitation) {

        List<GuestbookResponseDto> result = new ArrayList<>();

        List<Guestbook> guestbookList = invitation.getGuestbook();
        for (Guestbook guestbook : guestbookList) {
            result.add(new GuestbookResponseDto(guestbook));
        }

        return result;
    }
}
