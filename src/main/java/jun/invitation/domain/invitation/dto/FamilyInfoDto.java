package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.embedded.ParentDto;
import lombok.Data;

@Data
public class FamilyInfoDto {

    private String name;
    private ParentDto father;
    private ParentDto mother;

    private String relation;

    public FamilyInfoDto(FamilyInfo groomInfo) {
        this.name = groomInfo.getName();
        this.father = groomInfo.getFather();
        this.mother = groomInfo.getMother();

        this.relation = groomInfo.getRelation();
    }
}
