package jun.invitation.domain.invitation.dto;

import lombok.Data;

@Data
public class AccountInfoDto {
    private String account;

    private String fatherName;
    private String fatherAccount;

    private String motherName;
    private String motherAccount;

    public AccountInfoDto(String account, String fatherName, String fatherAccount, String motherName, String motherAccount) {
        this.account = account;
        this.fatherName = fatherName;
        this.fatherAccount = fatherAccount;
        this.motherName = motherName;
        this.motherAccount = motherAccount;
    }
}
