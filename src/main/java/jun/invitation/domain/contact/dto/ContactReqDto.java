package jun.invitation.domain.contact.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactReqDto {

    private List<ContactInfoDto> groom;

    private List<ContactInfoDto> bride;

}
