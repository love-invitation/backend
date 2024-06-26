package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class ArticleDto {
    private Integer priority;
    private String title;
    private String contents;

    private FamilyInfoDto groom;
    private FamilyInfoDto bride;

    public ArticleDto(String title, String contents, FamilyInfo groom, FamilyInfo bride, Integer priority) {
        this.priority = priority;
        this.title = title;
        this.contents = contents;

        if (groom != null) {
            this.groom = new FamilyInfoDto(groom);
        }
        if (bride != null) {
            this.bride = new FamilyInfoDto(bride);
        }
    }
}
