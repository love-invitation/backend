package jun.invitation.domain.contact.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactResDto {
    private Integer priority;

    private List<ContactInfoDto> groom;

    private List<ContactInfoDto> bride;


    public ContactResDto(List<ContactInfoDto> groomContactInfo, List<ContactInfoDto> brideContactInfo, Integer priority) {

        this.priority = priority;

        this.groom = groomContactInfo;
        this.bride = brideContactInfo;
    }
}
