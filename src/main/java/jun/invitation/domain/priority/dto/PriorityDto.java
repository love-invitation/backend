package jun.invitation.domain.priority.dto;

import jun.invitation.domain.priority.domain.Priority;
import lombok.*;

@Getter
@Setter @NoArgsConstructor
public class PriorityDto {
    private Integer article;
    private Integer reservation_date;
    private Integer reservation_place;
    private Integer transportation;
    private Integer contact;
    private Integer thumbnail;
    private Integer guestbook;

    @Builder
    public PriorityDto(Integer article, Integer reservation_date, Integer reservation_place,
                       Integer transportation, Integer contact, Integer thumbnail, Integer guestbook) {
        this.article = article;
        this.reservation_date = reservation_date;
        this.reservation_place = reservation_place;
        this.transportation = transportation;
        this.contact = contact;
        this.thumbnail = thumbnail;
        this.guestbook = guestbook;
    }

    public PriorityDto(Priority priority) {
        this.article = priority.getArticle();
        this.reservation_date = priority.getReservation_date();
        this.reservation_place = priority.getReservation_place();
        this.transportation = priority.getTransportation();
        this.contact = priority.getContact();
        this.thumbnail = priority.getThumbnail();
        this.guestbook = priority.getGuestbook();
    }
}
