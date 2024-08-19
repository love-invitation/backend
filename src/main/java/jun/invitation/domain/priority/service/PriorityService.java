package jun.invitation.domain.priority.service;

import jun.invitation.domain.account.dto.AccountInfoDto;
import jun.invitation.domain.account.dto.AccountResDto;
import jun.invitation.domain.account.service.AccountService;
import jun.invitation.domain.contact.dto.ContactInfoDto;
import jun.invitation.domain.contact.dto.ContactResDto;
import jun.invitation.domain.contact.service.ContactService;
import jun.invitation.domain.gallery.dto.GalleryInfoDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.dto.ArticleDto;
import jun.invitation.domain.invitation.dto.CoverDto;
import jun.invitation.domain.priority.PriorityName;
import jun.invitation.domain.priority.dao.PriorityRepository;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.priority.dto.PriorityDto;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.reservation.dto.DateDto;
import jun.invitation.domain.reservation.dto.PlaceDto;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.transport.dto.TransportInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jun.invitation.domain.invitation.domain.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.WeddingSide.GROOM;
import static jun.invitation.domain.priority.PriorityName.*;
import static jun.invitation.domain.priority.PriorityName.ACCOUNT;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PriorityService {

    private final PriorityRepository priorityRepository;
    private final ContactService contactService;
    private final AccountService accountService;

    public void create(List<PriorityDto> priorityDtos, Invitation invitation) {

        if (ObjectUtils.isEmpty(priorityDtos))
            return;

        priorityDtos.forEach(
                p -> new Priority(fromPriorityName(p.getName()), p.getPriority())
                        .register(invitation)
        );
    }

    public void delete(Priority priority) {
        priorityRepository.delete(priority);
    }

    public void delete(Long productId){
        priorityRepository.deleteByProductId(productId);
    }

    public void update(List<PriorityDto> newPriority, List<Priority> currentPriority) {
        Map<String, Integer> map = newPriority.stream()
                .collect(Collectors.toMap(PriorityDto::getName, PriorityDto::getPriority));

        currentPriority.forEach( p -> p.update(map.get(p.getName().getPriorityName())));
    }

    public void sortByPriority(Invitation invitation, LinkedHashMap<String, Object> result) {

        Reservation reservation = invitation.getReservation();
        result.put(TSID.getPriorityName(), invitation.getTsid());
        result.put("guestbookCheck", invitation.getGuestbookCheck());
        result.put(COVER.getPriorityName(), new CoverDto(invitation));

        for (Priority priority : invitation.getPriority()) {
            PriorityName name = priority.getName();
            Integer priorityValue = priority.getPriority();

            switch (name) {
                case ARTICLE:
                    result.put(ARTICLE.getPriorityName(),
                            new ArticleDto(invitation.getTitle(), invitation.getContents(),
                                    invitation.getGroomInfo(), invitation.getBrideInfo(), priorityValue)
                    );
                    break;
                case BOOKING:
                    result.put(BOOKING.getPriorityName(),
                            new DateDto(reservation, priorityValue)
                    );
                    break;
                case PLACE:
                    result.put(PLACE.getPriorityName(),
                            new PlaceDto(reservation, priorityValue)
                    );
                    break;
                case TRANSPORT:
                    result.put(TRANSPORT.getPriorityName(),
                            new TransportInfoDto(invitation.getTransport(), priorityValue)
                    );
                    break;
                case GALLERY:
                    result.put(GALLERY.getPriorityName(),
                            new GalleryInfoDto(invitation.getGallery(), priorityValue)
                    );
                    break;
                case CONTACT:
                    Map<String, List<ContactInfoDto>> classifiedContact = contactService.classifyByWeddingSide(invitation.getContacts());
                    result.put(CONTACT.getPriorityName(),
                            new ContactResDto(
                                    classifiedContact.get(GROOM.getSide()),
                                    classifiedContact.get(BRIDE.getSide()),
                                    priorityValue)
                    );
                    break;
                case ACCOUNT:
                    Map<String, List<AccountInfoDto>> classifiedMap = accountService.classifyBySide(invitation.getAccounts());
                    result.put(ACCOUNT.getPriorityName(), new AccountResDto(classifiedMap, priorityValue));
                    break;
            }
        }

        result.put(THUMBNAIL.getPriorityName(), new ShareThumbnailResDto(invitation.getShareThumbnail()));
    }
}