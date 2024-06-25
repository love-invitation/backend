package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.priority.dto.PriorityDto;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.transport.dto.TransportDto;
import jun.invitation.global.utils.PointUtils;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
public class InvitationDto {

    /* 상품 정보 id*/
    private Long productInfoId;

    private String coverContents;

    /* 모시는 글 */
    private String title;
    private String contents;

    /* 신랑 측 */
    private FamilyInfo groom;

    /* 신부 측 */
    private FamilyInfo bride;

    /* Wedding */
    // todo : 0625
    // wedding 삭제 -> ok.
    // 대신 booking, place로 분할해주기
//    private WeddingDto wedding;

    private WeddingPlaceReqDto place;
    private WeddingDateReqDto booking;


    /* 교통수단 */
    private List<TransportDto> transport;

    /* 연락처 */
    private ContactReqDto contacts;

    /* 계좌번호 */
    private AccountReqDto accounts;

    /* share thumbnail */
    private ShareThumbnailDto shareThumbnail;

    private Boolean guestbookCheck;

    /* 우선 순위 */
    private List<PriorityDto> priority;

    @Builder
    public InvitationDto(Long productInfoId, String title, String contents, List<TransportDto> transport, Boolean guestbookCheck,
                         List<PriorityDto> priority, FamilyInfo groom, FamilyInfo bride, String coverContents, ShareThumbnailDto shareThumbnail,
                         AccountReqDto accounts, ContactReqDto contacts, WeddingDateReqDto booking, WeddingPlaceReqDto place) {
        this.productInfoId = productInfoId;
        this.title = title;
        this.contents = contents;
        this.groom = groom;
        this.bride = bride;
        this.place = place;
        this.booking = booking;
        this.transport = transport;
        this.guestbookCheck = guestbookCheck;
        this.priority = priority;
        this.coverContents = coverContents;
        this.shareThumbnail = shareThumbnail;
        this.contacts = contacts;
        this.accounts = accounts;
    }

    public Invitation toInvitation() {

        return Invitation.builder()
                .title(title)
                .contents(contents)
                .wedding(
                        new Wedding(
                                place.getName(),
                                place.getDetail(),
                                place.getAddress(),
                                PointUtils.PointConvert(
                                        place.getLongitude(),
                                        place.getLatitude()
                                ),
                                booking.getDate(),
                                booking.getDateType()
                        )
                )
                .groomInfo(groom)
                .brideInfo(bride)
                .guestbookCheck(guestbookCheck)
                .coverContents(coverContents)
                .build();
    }
}
