package jun.invitation.domain.guestbook.service;

import jun.invitation.domain.guestbook.dao.GuestbookRepository;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.guestbook.dto.GuestbookDto;
import jun.invitation.domain.guestbook.dto.GuestbookResponseDto;
import jun.invitation.domain.guestbook.exception.GuestbookNotFoundException;
import jun.invitation.domain.guestbook.exception.PasswordMismatchException;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.service.InvitationService;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public Long create(GuestbookDto guestbookDto, Long productTsid) {

        Invitation invitation = invitationService.findByTsid(productTsid);
        String encodedPassword = passwordEncoder.encode(guestbookDto.getPassword());

        Guestbook guestbook = new Guestbook(
                guestbookDto.getName(),
                encodedPassword,
                guestbookDto.getMessage()
        );
        guestbook.registerInvitation(invitation);

        return guestbookRepository.save(guestbook).getId();
    }

    public Page<GuestbookResponseDto> getResponseDtoList(Long productTsid, Pageable pageable) {
        return guestbookRepository.findByProductTsidOrderByIdDesc(productTsid, pageable)
                .map(GuestbookResponseDto::new);
    }

    public void deleteAllByTsid(Long tsid) {
        Invitation invitation = invitationService.findByTsid(tsid);
        guestbookRepository.deleteByProductId(invitation.getId());
    }

    public void deleteGuestbook(Long productTsid, Long guestbookId, String password) {
        Product product = invitationService.findByTsid(productTsid);

        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .orElseThrow(GuestbookNotFoundException::new);

        Optional<String> passwordOpt = Optional.ofNullable(password);

        Optional.ofNullable(SecurityUtils.getCurrentUser())
                .ifPresentOrElse(u -> deleteByOwner(u, product, guestbook)
                        , () -> passwordOpt.ifPresent(p -> deleteByGuest(p, guestbook, product)));
    }

    private void deleteByGuest(String password, Guestbook guestbook, Product product) {
//        if (!password.equals(guestbook.getPassword()))
//            throw new PasswordMismatchException();
        if (passwordEncoder.matches(password, guestbook.getPassword())) {
            log.info("Deleting guestbook with password {}", password);
            delete(product, guestbook);
        }
        else
            throw new PasswordMismatchException();
    }

    private void deleteByOwner(User user, Product product, Guestbook guestbook) {
        if (Objects.equals(user.getId(), product.getUser().getId())) {
            delete(product, guestbook);
        }
    }

    private void delete(Product product, Guestbook guestbook) {
        guestbookRepository.delete(guestbook);
        product.getGuestbook().remove(guestbook);
    }
}
