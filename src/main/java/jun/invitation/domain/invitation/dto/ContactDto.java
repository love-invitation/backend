package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.BrideInfo;
import jun.invitation.domain.invitation.domain.GroomInfo;
import lombok.Data;

@Data
public class ContactDto {

    private Integer priority;

    private ContactInfoDto groomContactInfo;

    private ContactInfoDto brideContactInfo;


    public ContactDto(GroomInfo groomInfo, BrideInfo brideInfo, Integer priority) {

        this.priority = priority;

        if (groomInfo != null) {
            this.groomContactInfo = new ContactInfoDto(
                    groomInfo.getGroomPhone(),
                    groomInfo.getGroomFatherName(),
                    groomInfo.getGroomFatherPhone(),
                    groomInfo.getGroomMotherName(),
                    groomInfo.getGroomMotherPhone()
            );
        }

        if (brideInfo != null) {
            this.brideContactInfo = new ContactInfoDto(
                    brideInfo.getBridePhone(),
                    brideInfo.getBrideFatherName(),
                    brideInfo.getBrideFatherPhone(),
                    brideInfo.getBrideMotherName(),
                    brideInfo.getBrideMotherPhone()
            );
        }

    }
}
