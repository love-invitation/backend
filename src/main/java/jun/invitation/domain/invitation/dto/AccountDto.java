package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.BrideInfo;
import jun.invitation.domain.invitation.domain.GroomInfo;
import lombok.Data;

@Data
public class AccountDto {

    private Integer priority;

    private AccountInfoDto groomAccountInfo;
    private AccountInfoDto brideAccountInfo;

    public AccountDto(GroomInfo groomInfo, BrideInfo brideInfo, Integer priority) {

        this.priority = priority;

        this.groomAccountInfo = new AccountInfoDto(
                groomInfo.getGroomAccount(),
                groomInfo.getGroomFatherName(),
                groomInfo.getGroomFatherAccount(),
                groomInfo.getGroomMotherName(),
                groomInfo.getGroomMotherAccount()
        );

        this.brideAccountInfo = new AccountInfoDto(
                brideInfo.getBrideAccount(),
                brideInfo.getBrideFatherName(),
                brideInfo.getBrideFatherAccount(),
                brideInfo.getBrideMotherName(),
                brideInfo.getBrideMotherAccount()
        );
    }
}
