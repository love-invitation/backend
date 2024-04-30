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
    private Integer weddingDate;
    private Integer weddingPlace;
    private Integer transport;
    private Integer gallery;
    private Integer contact;
    private Integer thumbnail;
    private Integer guestbook;
    private Integer account;

    @Builder
    public Priority(Integer article, Integer weddingDate, Integer weddingPlace, Integer gallery,
                    Integer transport, Integer contact, Integer thumbnail, Integer guestbook, Integer account) {
        this.article = article;
        this.weddingDate = weddingDate;
        this.weddingPlace = weddingPlace;
        this.transport = transport;
        this.gallery = gallery;
        this.contact = contact;
        this.thumbnail = thumbnail;
        this.guestbook = guestbook;
        this.account = account;
    }
}
