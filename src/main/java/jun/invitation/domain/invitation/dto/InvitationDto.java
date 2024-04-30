package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data @Slf4j @NoArgsConstructor
public class InvitationDto {

    /* 상품 정보 id*/
    private Long productInfoId;

    private String title;
    private String contents;

    /* 신랑 측 */
    private GroomInfo groomInfo;

    /* 신부 측 */
    private BrideInfo brideInfo;

    /* Wedding */
    private Wedding wedding;

    /* 우선 순위 */
    private PriorityDto priorityDto;

    @Builder
    public InvitationDto(Long productInfoId, String title, String contents,
                         PriorityDto priorityDto, GroomInfo groomInfo, BrideInfo brideInfo, Wedding wedding) {
        this.productInfoId = productInfoId;
        this.title = title;
        this.contents = contents;
        this.groomInfo = groomInfo;
        this.brideInfo = brideInfo;
        this.wedding = wedding;
        this.priorityDto = priorityDto;
    }

    public Invitation toInvitation(){
        return Invitation.builder()
                .title(title)
                .contents(contents)
                .wedding(wedding)
                .groomInfo(groomInfo)
                .brideInfo(brideInfo)
                .build();
    }
}
