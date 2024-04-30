package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.BrideInfo;
import jun.invitation.domain.invitation.domain.GroomInfo;
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
        this.groomInfo = new GroomInfoDto(groomInfo);
        this.brideInfo = new BrideInfoDto(brideInfo);
    }
}