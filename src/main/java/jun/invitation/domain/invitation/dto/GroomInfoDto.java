package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.GroomInfo;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Data;

@Data
public class GroomInfoDto {
    private String name;
    private String fatherName;
    private String motherName;
    private String relation;

    public GroomInfoDto(GroomInfo groomInfo) {
        this.name = groomInfo.getGroomName();
        this.fatherName = groomInfo.getGroomFatherName();
        this.motherName = groomInfo.getGroomMotherName();
        this.relation = groomInfo.getGroomRelation();
    }
}
