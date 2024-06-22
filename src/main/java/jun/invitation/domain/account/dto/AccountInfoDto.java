package jun.invitation.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountInfoDto {

    private String name;
    private String bankName;
    private String accountNumber;

    public AccountInfoDto(String name, String accountNumber, String bankName) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
    }
}
