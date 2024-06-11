package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class FamilyInfoDto {
    private String name;
    private String fatherName;
    private String motherName;
    private String relation;

    public FamilyInfoDto(FamilyInfo groomInfo) {
        this.name = groomInfo.getName();
        this.fatherName = groomInfo.getFatherName();
        this.motherName = groomInfo.getMotherName();
        this.relation = groomInfo.getRelation();
    }
}
