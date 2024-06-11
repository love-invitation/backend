package jun.invitation.domain.guestbook.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Getter
@NoArgsConstructor
public class Guestbook {

    @Id @Column(name = "guestbook_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    public Guestbook(String name, String password, String message, Invitation invitation) {
        this.name = name;
        this.password = password;
        this.message = message;

        this.invitation = invitation;
        invitation.getGuestbook().add(this);
    }
}
