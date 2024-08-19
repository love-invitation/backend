package jun.invitation.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountReqDto {

    private List<AccountInfoDto> groom;

    private List<AccountInfoDto> bride;

}
