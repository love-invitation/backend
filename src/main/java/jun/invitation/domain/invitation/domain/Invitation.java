package jun.invitation.domain.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation extends Product {

    private String mainImageUrl;
    private String mainImageOriginName;
    private String mainImageStoreFileName;

    private String thumbnailContents;

    private String title;
    private String contents;

    @Embedded
    private Wedding wedding;

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    private List<Gallery> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    private List<Transport> transport = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    private List<Guestbook> guestbook = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = PERSIST)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    private Boolean guestbookCheck;

//    @Embedded
//    private BrideInfo brideInfo;
//
//    @Embedded
//    private GroomInfo groomInfo;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column( name = "brideName")),
        @AttributeOverride(name = "phone", column = @Column( name = "bridePhone")),
        @AttributeOverride(name = "bankName", column = @Column( name = "brideBankName")),
        @AttributeOverride(name = "account", column = @Column( name = "brideAccount")),

        @AttributeOverride(name = "fatherName", column = @Column( name = "brideFatherName")),
        @AttributeOverride(name = "fatherBankName", column = @Column( name = "brideFatherBankName")),
        @AttributeOverride(name = "fatherPhone", column = @Column( name = "brideFatherPhone")),
        @AttributeOverride(name = "fatherAccount", column = @Column( name = "brideFatherAccount")),

        @AttributeOverride(name = "motherName", column = @Column( name = "brideMotherName")),
        @AttributeOverride(name = "motherBankName", column = @Column( name = "brideMotherBankName")),
        @AttributeOverride(name = "motherPhone", column = @Column( name = "brideMotherPhone")),
        @AttributeOverride(name = "motherAccount", column = @Column( name = "brideMotherAccount")),
        @AttributeOverride(name = "relation", column = @Column( name = "brideRelation"))
    })
    private FamilyInfo brideInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column( name = "groomName")),
            @AttributeOverride(name = "phone", column = @Column( name = "groomPhone")),
            @AttributeOverride(name = "bankName", column = @Column( name = "groomBankName")),
            @AttributeOverride(name = "account", column = @Column( name = "groomAccount")),

            @AttributeOverride(name = "fatherName", column = @Column( name = "groomFatherName")),
            @AttributeOverride(name = "fatherBankName", column = @Column( name = "groomFatherBankName")),
            @AttributeOverride(name = "fatherPhone", column = @Column( name = "groomFatherPhone")),
            @AttributeOverride(name = "fatherAccount", column = @Column( name = "groomFatherAccount")),

            @AttributeOverride(name = "motherName", column = @Column( name = "groomMotherName")),
            @AttributeOverride(name = "motherBankName", column = @Column( name = "groomMotherBankName")),
            @AttributeOverride(name = "motherPhone", column = @Column( name = "groomMotherPhone")),
            @AttributeOverride(name = "motherAccount", column = @Column( name = "groomMotherAccount")),
            @AttributeOverride(name = "relation", column = @Column( name = "groomRelationAccount"))
    })
    private FamilyInfo groomInfo;


    public void registerMainImage(Map<String, String> savedFileMap) {
        this.mainImageOriginName = savedFileMap.get("originFileName");
        this.mainImageStoreFileName = savedFileMap.get("storeFileName");
        this.mainImageUrl = savedFileMap.get("imageUrl");
    }

    public void register(User user, ProductInfo productInfo, Priority priority) {
        super.register(user, productInfo);
        this.priority = priority;
    }

    @Builder
    public Invitation(String mainImageUrl, String thumbnailContents, String title, String contents, Wedding wedding, Boolean guestbookCheck,
                      Priority priority, FamilyInfo brideInfo, FamilyInfo groomInfo ) {
        this.mainImageUrl = mainImageUrl;
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.priority = priority;
        this.guestbookCheck = guestbookCheck;
        this.thumbnailContents = thumbnailContents;
    }

    public void update(InvitationDto invitationDto) {

        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();
        this.wedding = invitationDto.getWedding();
        this.brideInfo = invitationDto.getBrideInfo();
        this.groomInfo = invitationDto.getGroomInfo();
        this.guestbookCheck = invitationDto.getGuestbookCheck();
        this.thumbnailContents = invitationDto.getThumbnailContents();

        this.getPriority().update(invitationDto.getPriorityDto());
    }
}
