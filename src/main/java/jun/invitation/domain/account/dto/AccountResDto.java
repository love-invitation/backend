package jun.invitation.domain.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountResDto {

    private Integer priority;

    private List<AccountInfoDto> groom;
    private List<AccountInfoDto> bride;

    public AccountResDto(List<AccountInfoDto> groomAccountInfo, List<AccountInfoDto> brideAccountInfo, Integer priority) {

        this.priority = priority;

        this.groom = groomAccountInfo;
        this.bride = brideAccountInfo;

    }
}
