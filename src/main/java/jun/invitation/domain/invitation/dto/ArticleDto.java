package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class ArticleDto {
    private Integer priority;
    private String title;
    private String contents;

    private FamilyInfoDto groomInfo;
    private FamilyInfoDto brideInfo;

    public ArticleDto(String title, String contents, FamilyInfo groomInfo, FamilyInfo brideInfo, Integer priority) {
        this.priority = priority;
        this.title = title;
        this.contents = contents;

        if (groomInfo != null) {
            this.groomInfo = new FamilyInfoDto(groomInfo);
        }
        if (brideInfo != null) {
            this.brideInfo = new FamilyInfoDto(brideInfo);
        }
    }
}
