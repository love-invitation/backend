package jun.invitation.domain.product.invitation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jun.invitation.domain.product.invitation.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data @Slf4j @NoArgsConstructor
public class InvitationDto {

    private String title;
    private String contents;

    /* 신랑 측 */
    private MrFamily mrFamily;

    /* 신부 측 */
    private MrsFamily mrsFamily;

    /* Wedding */
    private Wedding wedding;

    /* theme */
    private Theme theme;



    public Invitation toInvitation(){
        return Invitation.builder()
                .title(title)
                .contents(contents)
                .wedding(wedding)
                .mrFamily(mrFamily)
                .mrsFamily(mrsFamily)
                .theme(theme)
                .build();
    }
}
