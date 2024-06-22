package jun.invitation.domain.account.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccountResDto {

    private Integer priority;

    private List<AccountInfoDto> groomAccountInfo;
    private List<AccountInfoDto> brideAccountInfo;

    public AccountResDto(List<AccountInfoDto> groomAccountInfo, List<AccountInfoDto> brideAccountInfo, Integer priority) {

        this.priority = priority;

        this.groomAccountInfo = groomAccountInfo;
        this.brideAccountInfo = brideAccountInfo;

    }
}
