package jun.invitation.domain.contact.dto;

import lombok.Data;

import java.util.List;

@Data
public class ContactReqDto {

    private List<ContactInfoDto> groom;

    private List<ContactInfoDto> bride;

}
