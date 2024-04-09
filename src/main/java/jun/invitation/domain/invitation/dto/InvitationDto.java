package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.priority.domain.Priority;
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
    private MrFamily mrFamily;

    /* 신부 측 */
    private MrsFamily mrsFamily;

    /* Wedding */
    private Wedding wedding;

    /* 우선 순위 */
    private PriorityDto priorityDto;

    @Builder
    public InvitationDto(Long productInfoId, String title, String contents,
                         PriorityDto priorityDto, MrFamily mrFamily, MrsFamily mrsFamily, Wedding wedding) {
        this.productInfoId = productInfoId;
        this.title = title;
        this.contents = contents;
        this.mrFamily = mrFamily;
        this.mrsFamily = mrsFamily;
        this.wedding = wedding;
        this.priorityDto = priorityDto;
    }

    public Invitation toInvitation(){
        return Invitation.builder()
                .title(title)
                .contents(contents)
                .wedding(wedding)
                .mrFamily(mrFamily)
                .mrsFamily(mrsFamily)
                .build();
    }
}
