package jun.invitation.domain.account.service;

import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.account.dto.AccountInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    public void save(List<AccountInfoDto> accountInfoDtos, Invitation invitation, String type) {

        if (accountInfoDtos == null) {
            return;
        }
        accountInfoDtos.stream()
                .map(a -> new Account(invitation, a.getName(), a.getBankName(), a.getAccountNumber(), type))
                .forEach(account -> account.register(invitation));
    }

    public Map<String, List> getSeperatedMap(List<Account> accounts) {

        List<AccountInfoDto> groomAccount = new ArrayList<>();
        List<AccountInfoDto> brideAccount = new ArrayList<>();

        accounts.forEach(account -> {
            if (account.getWeddingSide().equals("Bride"))
                brideAccount.add(new AccountInfoDto(
                                account.getName(),
                                account.getAccountNumber(),
                                account.getBankName()
                        )
                );
            else if (account.getWeddingSide().equals("Groom"))
                groomAccount.add(
                        new AccountInfoDto(
                                account.getName(),
                                account.getAccountNumber(),
                                account.getBankName()
                        )
                );
        });

        Map<String, List> seperatedMap = new HashMap<>();
        seperatedMap.put("brideAccount", brideAccount );
        seperatedMap.put("groomAccount", groomAccount);
        return seperatedMap;

    }
}
