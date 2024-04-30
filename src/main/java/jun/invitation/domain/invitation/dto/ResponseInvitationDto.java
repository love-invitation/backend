package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.priority.domain.Priority;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data @NoArgsConstructor @Slf4j
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

        Priority priority = invitation.getPriority();
        Wedding wedding = invitation.getWedding();
        GroomInfo groomInfo = invitation.getGroomInfo();
        BrideInfo brideInfo = invitation.getBrideInfo();

        this.id = invitation.getId();

        this.thumbnail = new ThumbnailDto( invitation);

        this.article = new ArticleDto(invitation.getTitle(), invitation.getContents(), groomInfo, brideInfo, priority.getArticle());

        this.weddingDate = new WeddingDateDto(wedding, priority.getWeddingDate());
        this.weddingPlace = new WeddingPlaceDto(wedding, priority.getWeddingPlace());

        this.transport = new TransportInfoDto(invitation.getTransport(), priority.getTransport());
        this.gallery = new GalleryInfoDto(invitation.getGallery(), priority.getGallery());

        log.info("[priority.getContact()] {}" , priority.getContact());
        log.info("[priority.getAccount()] {}" , priority.getAccount());
        this.contact = new ContactDto(groomInfo,brideInfo, priority.getContact());
        this.account = new AccountDto(groomInfo,brideInfo,priority.getAccount());

    }
}
