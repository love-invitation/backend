package jun.invitation.domain.contact.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactResDto {
    private Integer priority;

    private List<ContactInfoDto> groomContactInfo;

    private List<ContactInfoDto> brideContactInfo;


    public ContactResDto(List<ContactInfoDto> groomContactInfo, List<ContactInfoDto> brideContactInfo, Integer priority) {

        this.priority = priority;

        this.groomContactInfo = groomContactInfo;
        this.brideContactInfo = brideContactInfo;
    }
}
