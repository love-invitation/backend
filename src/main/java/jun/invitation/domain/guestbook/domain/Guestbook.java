package jun.invitation.domain.guestbook.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Getter
@NoArgsConstructor
public class Guestbook {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "guestbook_id")
    private Long id;

    private String name;
    private String password;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Invitation invitation;

    public Guestbook(String name, String password, String message) {
        this.name = name;
        this.password = password;
        this.message = message;
    }

    public void registerInvitation(Invitation invitation) {
        if (this.invitation != null) {
            this.invitation.getGuestbook().remove(this);
        }
        this.invitation = invitation;
        invitation.getGuestbook().add(this);
    }
}
