package jun.invitation.domain.guestbook.service;

import jakarta.transaction.Transactional;
import jun.invitation.domain.guestbook.dao.GuestbookRepository;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.execption.GuestbookNotFoundException;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.exception.PasswordMismatchException;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public Long requestCreate(GuestbookDto guestbookDto, Invitation invitation) {

        Guestbook guestbook = new Guestbook(
                guestbookDto.getName(),
                guestbookDto.getPassword(),
                guestbookDto.getMessage()
        );
        guestbook.registerInvitation(invitation);

        Guestbook savedGuestbook = guestbookRepository.save(guestbook);
        return savedGuestbook.getId();
    }

    public List<Guestbook> requestAll() {
        return guestbookRepository.findAll();
    }

    public Page<GuestbookResponseDto> getResponseDtoList(Long invitationId, Pageable pageable) {
        return guestbookRepository.findByInvitation_idOrderByIdDesc(invitationId, pageable)
                .map(guestbook -> new GuestbookResponseDto(guestbook));
    }

    public void delete(Invitation invitation, Long guestbookId) {

        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElseThrow(GuestbookNotFoundException::new);

        User currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getId() == invitation.getUser().getId()){
            guestbookRepository.delete(guestbook);
            invitation.getGuestbook().remove(guestbook);
        }
    }

    public void delete(Invitation invitation, Long guestbookId, String password) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId).orElseThrow(GuestbookNotFoundException::new);

        if (guestbook.getPassword().equals(password)) {
            invitation.getGuestbook().remove(guestbook);
            guestbookRepository.delete(guestbook);
        } else {
            throw new PasswordMismatchException(password);
        }
    }

    @Transactional
    public void delete(Long invitationId) {
        guestbookRepository.deleteByInvitationId(invitationId);
    }
}
