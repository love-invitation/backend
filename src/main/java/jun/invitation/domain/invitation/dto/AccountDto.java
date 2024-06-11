package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import lombok.Data;

@Data
public class AccountDto {

    private Integer priority;

    private AccountInfoDto groomAccountInfo;
    private AccountInfoDto brideAccountInfo;

    public AccountDto(FamilyInfo groomInfo, FamilyInfo brideInfo, Integer priority) {

        this.priority = priority;

        if (groomInfo != null){
            this.groomAccountInfo = new AccountInfoDto(
                    groomInfo.getAccount(),
                    groomInfo.getBankName(),
                    groomInfo.getFatherName(),
                    groomInfo.getFatherBankName(),
                    groomInfo.getFatherAccount(),
                    groomInfo.getMotherName(),
                    groomInfo.getMotherBankName(),
                    groomInfo.getMotherAccount()
            );
        }

        if (brideInfo != null) {
            this.brideAccountInfo = new AccountInfoDto(
                    brideInfo.getAccount(),
                    brideInfo.getBankName(),
                    brideInfo.getFatherName(),
                    brideInfo.getFatherBankName(),
                    brideInfo.getFatherAccount(),
                    brideInfo.getMotherName(),
                    brideInfo.getMotherBankName(),
                    brideInfo.getMotherAccount()
            );
        }
    }
}
