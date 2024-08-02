package jun.invitation.domain.guestbook.service;

import jun.invitation.domain.guestbook.dao.GuestbookRepository;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.execption.GuestbookNotFoundException;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;
    private final InvitationService invitationService;

    public Long create(GuestbookDto guestbookDto, Long productId) {

        Invitation invitation = invitationService.findById(productId);

        Guestbook guestbook = new Guestbook(
                guestbookDto.getName(),
                guestbookDto.getPassword(),
                guestbookDto.getMessage()
        );
        guestbook.registerInvitation(invitation);

        Guestbook savedGuestbook = guestbookRepository.save(guestbook);
        return savedGuestbook.getId();
    }

    public Page<GuestbookResponseDto> getResponseDtoList(Long invitationId, Pageable pageable) {
        return guestbookRepository.findByProduct_idOrderByIdDesc(invitationId, pageable)
                .map(GuestbookResponseDto::new);
    }

    public void delete(Product product, Long guestbookId, String password) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(GuestbookNotFoundException::new);

        Optional<String> passwordOpt = Optional.ofNullable(password);

        Optional.ofNullable(SecurityUtils.getCurrentUser())
                .ifPresentOrElse(u -> {
                            if (Objects.equals(u.getId(), product.getUser().getId())) {
                                guestbookRepository.delete(guestbook);
                                product.getGuestbook().remove(guestbook);
                            }
                        }, () -> passwordOpt.ifPresent(p -> {
                            if (p.equals(guestbook.getPassword())) {
                                product.getGuestbook().remove(guestbook);
                                guestbookRepository.delete(guestbook);
                            }
                        })
                );
    }

    public void delete(Long tsid) {
        Invitation invitation = invitationService.findByTsid(tsid);
        guestbookRepository.deleteByProductId(invitation.getId());
    }

    public void deleteGuestbook(Long productId, Long guestbookId, String password) {
        Invitation invitation = invitationService.findById(productId);
        delete(invitation,guestbookId, password);
    }
}
