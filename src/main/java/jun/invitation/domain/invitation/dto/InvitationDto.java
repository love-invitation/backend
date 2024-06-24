package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.contact.dto.ContactReqDto;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.priority.dto.PriorityDto;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailDto;
import jun.invitation.domain.transport.dto.TransportDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

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
    private FamilyInfo groomInfo;

    /* 신부 측 */
    private FamilyInfo brideInfo;

    /* Wedding */
    private WeddingDto wedding;

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
                         List<PriorityDto> priority, FamilyInfo groomInfo, FamilyInfo brideInfo, WeddingDto wedding, String coverContents, ShareThumbnailDto shareThumbnail, AccountReqDto accounts, ContactReqDto contacts) {
        this.productInfoId = productInfoId;
        this.title = title;
        this.contents = contents;
        this.groomInfo = groomInfo;
        this.brideInfo = brideInfo;
        this.wedding = wedding;
        this.transport = transport;
        this.guestbookCheck = guestbookCheck;
        this.priority = priority;
        this.coverContents = coverContents;
        this.shareThumbnail = shareThumbnail;
        this.contacts = contacts;
        this.accounts = accounts;
    }

    public Invitation toInvitation() {
        Point point = null;
        try {
            String pointWKT = String.format("POINT(%s %s)", wedding.getLongitude(), wedding.getLatitude());
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            log.info("[message : WKTReader().read(pointWKT) 수행 중 ParseException] 발생, point = null 후 정상 흐름으로 이어가겠습니다.");
            point = null;
        }
        return Invitation.builder()
                .title(title)
                .contents(contents)
                .wedding(
                        new Wedding(
                                wedding.getPlaceName(),
                                wedding.getDetail(),
                                wedding.getPlaceAddress(),
                                point,
                                wedding.getDate(),
                                wedding.getDateType()
                        )
                )
                .groomInfo(groomInfo)
                .brideInfo(brideInfo)
                .guestbookCheck(guestbookCheck)
                .coverContents(coverContents)
                .build();
    }
}
