package jun.invitation.domain.gallery;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long priority;

    private String originFileName;
    private String storeFileName;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    public Gallery(String originFileName , String storeFileName, Long priority, String imageUrl) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
        this.priority = priority;
        this.imageUrl = imageUrl;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
        invitation.getGallery().add(this);
    }

}
