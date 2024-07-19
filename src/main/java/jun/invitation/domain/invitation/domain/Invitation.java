package jun.invitation.domain.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.invitation.dto.WeddingDateReqDto;
import jun.invitation.domain.invitation.dto.WeddingPlaceReqDto;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.global.utils.PointUtils;
import jun.invitation.image.domain.Image;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jun.invitation.aws.s3.ImageUploadKey.*;

@Entity
@Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Invitation extends Product {

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image mainImage;

    private String coverContents;

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

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = PERSIST)
    @OrderBy("priority")
    private List<Priority> priority = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "share_thumbnail_id")
    private ShareThumbnail shareThumbnail;

    private Boolean guestbookCheck;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column( name = "brideName")),
        @AttributeOverride(name = "phone", column = @Column( name = "bridePhone")),

        @AttributeOverride(name = "father.name", column = @Column( name = "brideFatherName")),
        @AttributeOverride(name = "father.deceased", column = @Column( name = "brideFatherIsDeceased")),

        @AttributeOverride(name = "mother.name", column = @Column( name = "brideMotherName")),
        @AttributeOverride(name = "mother.deceased", column = @Column( name = "brideMotherIsDeceased")),

        @AttributeOverride(name = "relation", column = @Column( name = "brideRelation"))
    })
    private FamilyInfo brideInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column( name = "groomName")),
            @AttributeOverride(name = "phone", column = @Column( name = "groomPhone")),

            @AttributeOverride(name = "father.name", column = @Column( name = "groomFatherName")),
            @AttributeOverride(name = "father.deceased", column = @Column( name = "groomFatherIsDeceased")),

            @AttributeOverride(name = "mother.name", column = @Column( name = "groomMotherName")),
            @AttributeOverride(name = "mother.deceased", column = @Column( name = "groomMotherIsDeceased")),

            @AttributeOverride(name = "relation", column = @Column( name = "groomRelation"))
    })
    private FamilyInfo groomInfo;


    public void registerMainImage(Image image) {
        this.mainImage = image;
    }

    public void registerShareThumbnail(ShareThumbnail shareThumbnail) {
        this.shareThumbnail = shareThumbnail;
    }

    @Builder
    public Invitation(String coverContents, String title, String contents, Wedding wedding, Boolean guestbookCheck,
                      FamilyInfo brideInfo, FamilyInfo groomInfo) {
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.guestbookCheck = guestbookCheck;
        this.coverContents = coverContents;
    }

    public void update(InvitationDto invitationDto) {

        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();

        WeddingDateReqDto booking = invitationDto.getBooking();
        WeddingPlaceReqDto place = invitationDto.getPlace();

        if (booking != null) {
            wedding.updateBooking(
                    booking.getDate(),
                    booking.getDateType()
            );
        }

        if (place != null) {
            wedding.updatePlace(
                    place.getName(),
                    place.getDetail(),
                    place.getAddress(),
                    PointUtils.PointConvert(
                            place.getLongitude(),
                            place.getLongitude()
                    )
            );
        }

        this.brideInfo = invitationDto.getBride();
        this.groomInfo = invitationDto.getGroom();
        this.guestbookCheck = invitationDto.getGuestbookCheck();
        this.coverContents = invitationDto.getCoverContents();

    }
}
