package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.BrideInfo;
import lombok.Data;

@Data
public class BrideInfoDto {
    private String name;
    private String fatherName;
    private String motherName;
    private String relation;

    public BrideInfoDto(BrideInfo brideInfo) {
        this.name = brideInfo.getBrideName();
        this.fatherName = brideInfo.getBrideFatherName();
        this.motherName = brideInfo.getBrideMotherName();
        this.relation = brideInfo.getBrideRelation();
    }
}