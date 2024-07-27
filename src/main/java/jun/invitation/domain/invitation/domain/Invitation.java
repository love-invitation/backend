package jun.invitation.domain.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.invitation.dto.InvitationDto;
import jun.invitation.domain.reservation.dto.WeddingDateReqDto;
import jun.invitation.domain.reservation.dto.WeddingPlaceReqDto;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.global.utils.PointUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Invitation extends Product {

    private String coverContents;

    private String title;
    private String contents;

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

    @Builder
    public Invitation(String coverContents, String title, String contents, Boolean guestbookCheck,
                      FamilyInfo brideInfo, FamilyInfo groomInfo) {
        super(guestbookCheck);
        this.title = title;
        this.contents = contents;
        this.brideInfo = brideInfo;
        this.groomInfo = groomInfo;
        this.coverContents = coverContents;
    }

    public void update(InvitationDto invitationDto) {
        super.update(invitationDto.getGuestbookCheck());
        this.title = invitationDto.getTitle();
        this.contents = invitationDto.getContents();
        this.brideInfo = invitationDto.getBride();
        this.groomInfo = invitationDto.getGroom();
        this.coverContents = invitationDto.getCoverContents();
    }
}
