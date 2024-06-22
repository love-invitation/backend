package jun.invitation.domain.contact.dto;

import jun.invitation.domain.invitation.dto.ContactInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class ContactReqDto {

    private List<ContactInfoDto> groomContactInfo;

    private List<ContactInfoDto> brideContactInfo;

}
