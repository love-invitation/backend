package jun.invitation.domain.invitation.dto;

import lombok.Data;

@Data
public class AccountInfoDto {
    private String bankName;
    private String account;

    private String fatherName;
    private String fatherBankName;
    private String fatherAccount;

    private String motherName;
    private String motherBankName;
    private String motherAccount;

    public AccountInfoDto(String account, String bankName, String fatherName, String fatherBankName, String fatherAccount, String motherName,String motherBankName, String motherAccount) {

        this.account = account;
        this.bankName = bankName;

        this.fatherName = fatherName;
        this.fatherBankName = fatherBankName;
        this.fatherAccount = fatherAccount;

        this.motherName = motherName;
        this.motherBankName = motherBankName;
        this.motherAccount = motherAccount;
    }
}
