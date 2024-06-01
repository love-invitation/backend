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
                groomInfo.getGroomBankName(),
                groomInfo.getGroomFatherName(),
                groomInfo.getGroomFatherBankName(),
                groomInfo.getGroomFatherAccount(),
                groomInfo.getGroomMotherName(),
                groomInfo.getGroomMotherBankName(),
                groomInfo.getGroomMotherAccount()
        );

        this.brideAccountInfo = new AccountInfoDto(
                brideInfo.getBrideAccount(),
                brideInfo.getBrideBankName(),
                brideInfo.getBrideFatherName(),
                brideInfo.getBrideFatherBankName(),
                brideInfo.getBrideFatherAccount(),
                brideInfo.getBrideMotherName(),
                brideInfo.getBrideMotherBankName(),
                brideInfo.getBrideMotherAccount()
        );
    }
}
