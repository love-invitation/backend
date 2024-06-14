package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.priority.domain.Priority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ResponseInvitationDto {

    private Long id;

    private ThumbnailDto thumbnail;

    private ArticleDto article;

    private WeddingDateDto weddingDate;

    private WeddingPlaceDto weddingPlace;

    private TransportInfoDto transport;

    private GalleryInfoDto gallery;

    private AccountDto account;

    private ContactDto contact;

    public ResponseInvitationDto(Invitation invitation) {
        List<Priority> priorities = invitation.getPriority();

        Map<String, Integer> map = invitation.getPriority()
                .stream()
                .collect(Collectors.toMap(Priority::getName, Priority::getPriority));

        Wedding wedding = invitation.getWedding();
        FamilyInfo groomInfo = invitation.getGroomInfo();
        FamilyInfo brideInfo = invitation.getBrideInfo();

        this.id = invitation.getId();

        this.thumbnail = new ThumbnailDto( invitation);

        this.article = new ArticleDto(invitation.getTitle(), invitation.getContents(), groomInfo, brideInfo, map.get("article"));

        this.weddingDate = new WeddingDateDto(wedding, map.get("weddingDate"));
        this.weddingPlace = new WeddingPlaceDto(wedding, map.get("weddingPlace"));

        this.transport = new TransportInfoDto(invitation.getTransport(), map.get("transport"));
        this.gallery = new GalleryInfoDto(invitation.getGallery(), map.get("gallery"));

        this.contact = new ContactDto(groomInfo,brideInfo, map.get("contact"));
        this.account = new AccountDto(groomInfo,brideInfo, map.get("account"));
    }
}
