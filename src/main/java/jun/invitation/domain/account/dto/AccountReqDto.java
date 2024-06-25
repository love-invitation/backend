package jun.invitation.domain.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountReqDto {

    private List<AccountInfoDto> groom;

    private List<AccountInfoDto> bride;

}
