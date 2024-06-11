package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class ContactDto {

    private Integer priority;

    private ContactInfoDto groomContactInfo;

    private ContactInfoDto brideContactInfo;


    public ContactDto(FamilyInfo groomInfo, FamilyInfo brideInfo, Integer priority) {

        this.priority = priority;

        if (groomInfo != null) {
            this.groomContactInfo = new ContactInfoDto(
                    groomInfo.getPhone(),
                    groomInfo.getFatherName(),
                    groomInfo.getFatherPhone(),
                    groomInfo.getMotherName(),
                    groomInfo.getMotherPhone()
            );
        }

        if (brideInfo != null) {
            this.brideContactInfo = new ContactInfoDto(
                    brideInfo.getPhone(),
                    brideInfo.getFatherName(),
                    brideInfo.getFatherPhone(),
                    brideInfo.getMotherName(),
                    brideInfo.getMotherPhone()
            );
        }

    }
}
