package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

import java.util.List;

@Data
public class ContactDto {

    private Integer priority;

    private List<ContactInfoDto> groomContactInfo;

    private List<ContactInfoDto> brideContactInfo;


    public ContactDto(List<ContactInfoDto> groomContactInfo, List<ContactInfoDto> brideContactInfo, Integer priority) {

        this.priority = priority;

        this.groomContactInfo = groomContactInfo;
        this.brideContactInfo = brideContactInfo;
    }
}
