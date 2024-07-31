package jun.invitation.domain.account.dto;

import jun.invitation.domain.account.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

    public AccountInfoDto(Account account) {
        this.name = account.getName();
        this.accountNumber = account.getAccountNumber();
        this.bankName = account.getBankName();
    }
}
