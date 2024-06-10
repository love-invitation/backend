package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.BrideInfo;
import jun.invitation.domain.invitation.domain.embedded.GroomInfo;
import lombok.Data;

@Data
public class ArticleDto {
    private Integer priority;
    private String title;
    private String contents;

    private GroomInfoDto groomInfo;
    private BrideInfoDto brideInfo;

    public ArticleDto(String title, String contents, GroomInfo groomInfo, BrideInfo brideInfo, Integer priority) {
        this.priority = priority;
        this.title = title;
        this.contents = contents;

        if (groomInfo != null) {
            this.groomInfo = new GroomInfoDto(groomInfo);
        }
        if (brideInfo != null) {
            this.brideInfo = new BrideInfoDto(brideInfo);
        }
    }
}
