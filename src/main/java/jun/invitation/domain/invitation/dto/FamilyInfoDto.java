package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class FamilyInfoDto {

    private String name;
    private String fatherName;
    private Boolean isFatherCondolences;
    private String motherName;
    private Boolean isMotherCondolences;

    private String relation;

    public FamilyInfoDto(FamilyInfo groomInfo) {
        this.name = groomInfo.getName();
        this.fatherName = groomInfo.getFatherName();
        this.isFatherCondolences = groomInfo.getFatherIsCondolences();
        this.motherName = groomInfo.getMotherName();
        this.isMotherCondolences = groomInfo.getMotherIsCondolences();
        this.relation = groomInfo.getRelation();
    }
}
