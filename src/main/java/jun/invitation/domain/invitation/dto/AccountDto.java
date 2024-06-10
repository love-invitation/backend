package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.BrideInfo;
import jun.invitation.domain.invitation.domain.embedded.GroomInfo;
import lombok.Data;

@Data
public class AccountDto {

    private Integer priority;

    private AccountInfoDto groomAccountInfo;
    private AccountInfoDto brideAccountInfo;

    public AccountDto(GroomInfo groomInfo, BrideInfo brideInfo, Integer priority) {

        this.priority = priority;

        if (groomInfo != null){
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
        }

        if (brideInfo != null) {
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
}
