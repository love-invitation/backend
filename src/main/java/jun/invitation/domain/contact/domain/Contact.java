package jun.invitation.domain.contact.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import jun.invitation.domain.priority.PriorityName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Invitation invitation;

    private String name;
    private String phoneNumber;
    private String relation;

    @Enumerated(STRING)
    private WeddingSide weddingSide;

    public Contact(String name, String phoneNumber, String relation, WeddingSide weddingSide) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
        this.weddingSide = weddingSide;
    }

    public void register(Invitation invitation) {

        if (this.invitation != null) {
            this.invitation.getContacts().remove(this);
        }

        this.invitation = invitation;
        invitation.getContacts().add(this);
    }
}
