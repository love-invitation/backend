package jun.invitation.domain.gallery;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.image.domain.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Getter
@NoArgsConstructor
public class Gallery {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "gallery_id")
    private Long id;

    private Long priority;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Invitation invitation;

    @OneToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image_id")
    private Image image;

    public Gallery(Long priority, Image image) {
        this.image = image;
        this.priority = priority;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
        invitation.getGallery().add(this);
    }

}
