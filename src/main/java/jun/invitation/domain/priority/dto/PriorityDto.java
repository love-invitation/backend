package jun.invitation.domain.priority.dto;

import jun.invitation.domain.priority.domain.Priority;
import lombok.*;

@Getter
@Setter @NoArgsConstructor
public class PriorityDto {
    private Integer article;
    private Integer weddingDate;
    private Integer weddingPlace;
    private Integer transport;
    private Integer contact;
    private Integer thumbnail;
    private Integer guestbook;
    private Integer account;
    private Integer gallery;

    @Builder
    public PriorityDto(Integer article, Integer weddingDate, Integer weddingPlace, Integer gallery,
                       Integer transport, Integer contact, Integer thumbnail, Integer guestbook, Integer account) {
        this.article = article;
        this.weddingDate = weddingDate;
        this.weddingPlace = weddingPlace;
        this.transport = transport;
        this.contact = contact;
        this.thumbnail = thumbnail;
        this.guestbook = guestbook;
        this.account = account;
        this.gallery = gallery;
    }

    public PriorityDto(Priority priority) {
        this.article = priority.getArticle();
        this.weddingDate = priority.getWeddingDate();
        this.weddingPlace = priority.getWeddingPlace();
        this.transport = priority.getTransport();
        this.contact = priority.getContact();
        this.thumbnail = priority.getThumbnail();
        this.guestbook = priority.getGuestbook();
        this.account = priority.getAccount();
        this.gallery = priority.getGallery();
    }
}
