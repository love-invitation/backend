package jun.invitation.domain.product.invitation.domain.Gallery;

import jakarta.persistence.*;
import jun.invitation.domain.product.invitation.domain.Invitation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long priority;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    public Gallery(Long priority, String imageUrl) {
        this.priority = priority;
        this.imageUrl = imageUrl;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
        invitation.getGallery().add(this);
    }

}