package jun.invitation.domain.product.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.product.invitation.domain.Gallery.Gallery;
import jun.invitation.domain.product.invitation.dto.InvitationDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity @Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class Invitation extends Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    private String mainImageUrl;
    private String mainImageOriginName;
    private String mainImageStoreFileName;

    private String title;
    private String contents;

    @Embedded
    private Wedding wedding;

    @OneToMany(mappedBy = "invitation" ,cascade = CascadeType.ALL)
    private List<Gallery> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.ALL)
    private List<Transport> transport = new ArrayList<>();

    @Embedded
    private Theme theme;

    @Embedded
    private MrsFamily mrsFamily;

    @Embedded
    private MrFamily mrFamily;

    public void registerMainImage(Map<String, String> savedFileMap) {
        this.mainImageOriginName = savedFileMap.get("originFileName");
        this.mainImageStoreFileName = savedFileMap.get("storeFileName");
        this.mainImageUrl = savedFileMap.get("imageUrl");
    }

    @Builder
    public Invitation(String mainImageUrl, String title, String contents, Wedding wedding,
                      Theme theme, MrsFamily mrsFamily, MrFamily mrFamily) {
        this.mainImageUrl = mainImageUrl;
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.theme = theme;
        this.mrsFamily = mrsFamily;
        this.mrFamily = mrFamily;
    }

    public void update(InvitationDto invitationDto) {

        this.mainImageUrl = null;
        this.mainImageOriginName = null;
        this.mainImageStoreFileName = null;

        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();
        this.wedding = invitationDto.getWedding();
        this.theme = invitationDto.getTheme();
        this.mrsFamily = invitationDto.getMrsFamily();
        this.mrFamily = invitationDto.getMrFamily();

        this.gallery.clear();
        this.transport.clear();
    }
}
