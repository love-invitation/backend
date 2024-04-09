package jun.invitation.domain.priority.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity @NoArgsConstructor
@AllArgsConstructor
@Getter
public class Priority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id")
    private Long id;

    private Integer article;
    private Integer reservation_date;
    private Integer reservation_place;
    private Integer transportation;
    private Integer contact;
    private Integer thumbnail;
    private Integer guestbook;

    @Builder
    public Priority(Integer article, Integer reservation_date, Integer reservation_place,
                    Integer transportation, Integer contact, Integer thumbnail, Integer guestbook) {
        this.article = article;
        this.reservation_date = reservation_date;
        this.reservation_place = reservation_place;
        this.transportation = transportation;
        this.contact = contact;
        this.thumbnail = thumbnail;
        this.guestbook = guestbook;
    }
}
