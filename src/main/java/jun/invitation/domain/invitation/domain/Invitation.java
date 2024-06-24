package jun.invitation.domain.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.transport.domain.Transport;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Invitation extends Product {

    private String mainImageUrl;
    private String mainImageOriginName;
    private String mainImageStoreFileName;

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

        @AttributeOverride(name = "fatherName", column = @Column( name = "brideFatherName")),
        @AttributeOverride(name = "fatherIsCondolences", column = @Column( name = "brideFatherIsCondolences")),

        @AttributeOverride(name = "motherName", column = @Column( name = "brideMotherName")),
        @AttributeOverride(name = "MotherIsCondolences", column = @Column( name = "brideMotherIsCondolences")),

        @AttributeOverride(name = "relation", column = @Column( name = "brideRelation"))
    })
    private FamilyInfo brideInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column( name = "groomName")),
            @AttributeOverride(name = "phone", column = @Column( name = "groomPhone")),

            @AttributeOverride(name = "fatherName", column = @Column( name = "groomFatherName")),
            @AttributeOverride(name = "fatherIsCondolences", column = @Column( name = "groomFatherIsCondolences")),

            @AttributeOverride(name = "motherName", column = @Column( name = "groomMotherName")),
            @AttributeOverride(name = "motherIsCondolences", column = @Column( name = "groomMotherIsCondolences")),

            @AttributeOverride(name = "relation", column = @Column( name = "groomRelationAccount"))
    })
    private FamilyInfo groomInfo;


    public void registerMainImage(Map<String, String> savedFileMap) {

        if (savedFileMap == null || savedFileMap.isEmpty()) {
            this.mainImageOriginName = null;
            this.mainImageStoreFileName = null;
            this.mainImageUrl = null;

            return;
        }

        this.mainImageOriginName = savedFileMap.get("originFileName");
        this.mainImageStoreFileName = savedFileMap.get("storeFileName");
        this.mainImageUrl = savedFileMap.get("imageUrl");
    }

    public void registerShareThumbnail(ShareThumbnail shareThumbnail) {
        this.shareThumbnail = shareThumbnail;
    }

    @Builder
    public Invitation(String mainImageUrl, String coverContents, String title, String contents, Wedding wedding, Boolean guestbookCheck,
                      FamilyInfo brideInfo, FamilyInfo groomInfo) {
        this.mainImageUrl = mainImageUrl;
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.guestbookCheck = guestbookCheck;
        this.coverContents = coverContents;
    }

    /**
     * TODO : shareThumbnail 관련 코드 다시 확인
     * @param invitationDto
     */
    public void update(InvitationDto invitationDto) {

        // WKTReader를 통해 WKT -> 실제 타입으로 변환
        Point point = null;
        try {
            String pointWKT = String.format("POINT(%s %s)", invitationDto.getWedding().getLongitude(), invitationDto.getWedding().getLatitude());
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            log.info("[message : WKTReader().read(pointWKT) 수행 중 ParseException] 발생, point = null 후 정상 흐름으로 이어가겠습니다.");
            point = null;
        }

        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();
        this.wedding = new Wedding(
                invitationDto.getWedding().getPlaceName(),
                invitationDto.getWedding().getDetail(),
                invitationDto.getWedding().getPlaceAddress(),
                point,
                invitationDto.getWedding().getDate(),
                invitationDto.getWedding().getDateType()
        );
        this.brideInfo = invitationDto.getBrideInfo();
        this.groomInfo = invitationDto.getGroomInfo();
        this.guestbookCheck = invitationDto.getGuestbookCheck();
        this.coverContents = invitationDto.getCoverContents();

    }
}
