package jun.invitation.domain.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.user.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.FetchType.*;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @Embedded
    private BrideInfo brideInfo;

    @Embedded
    private GroomInfo groomInfo;

    public void registerMainImage(Map<String, String> savedFileMap) {
        this.mainImageOriginName = savedFileMap.get("originFileName");
        this.mainImageStoreFileName = savedFileMap.get("storeFileName");
        this.mainImageUrl = savedFileMap.get("imageUrl");
    }

    public void registerUserProductInfo(User user, ProductInfo productInfo, Priority priority) {
        super.registerUserProductInfo(user, productInfo);
        this.priority = priority;
    }

    @Builder
    public Invitation(String mainImageUrl, String title, String contents, Wedding wedding,
                      Priority priority, BrideInfo brideInfo, GroomInfo groomInfo) {
        this.mainImageUrl = mainImageUrl;
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.priority = priority;
    }

    public void update(InvitationDto invitationDto) {

        this.mainImageUrl = null;
        this.mainImageOriginName = null;
        this.mainImageStoreFileName = null;

        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();
        this.wedding = invitationDto.getWedding();
        this.brideInfo = invitationDto.getBrideInfo();
        this.groomInfo = invitationDto.getGroomInfo();

        this.gallery.clear();
        this.transport.clear();
    }

    public void registerPriority(Priority priority) {
        this.priority = priority;
    }
}
