package jun.invitation.domain.account.service;

import jun.invitation.domain.account.dao.AccountRepository;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.account.dto.AccountInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public void save(List<AccountInfoDto> accountInfoDtos, Invitation invitation, String type) {

        if (accountInfoDtos == null) {
            return;
        }
        accountInfoDtos.stream()
                .map(a -> new Account(a.getName(), a.getBankName(), a.getAccountNumber(), type))
                .forEach(account -> account.register(invitation));
    }

    public Map<String, List> getSeperatedMap(List<Account> accounts) {

        List<AccountInfoDto> groom = new ArrayList<>();
        List<AccountInfoDto> bride = new ArrayList<>();

        accounts.forEach(account -> {
            if (account.getWeddingSide().equals("Bride"))
                bride.add(
                        new AccountInfoDto(
                                account.getName(),
                                account.getAccountNumber(),
                                account.getBankName()
                        )
                );
            else if (account.getWeddingSide().equals("Groom"))
                groom.add(
                        new AccountInfoDto(
                                account.getName(),
                                account.getAccountNumber(),
                                account.getBankName()
                        )
                );
        });

        Map<String, List> seperatedMap = new HashMap<>();
        seperatedMap.put("bride", bride );
        seperatedMap.put("groom", groom);
        return seperatedMap;

    }

    public void delete(Long productId) {
        accountRepository.deleteByProductId(productId);
    }

    public void update(AccountReqDto newAccounts, List<Account> currentAccounts, Invitation invitation) {

        if (!currentAccounts.isEmpty() || currentAccounts != null) {
            log.info("!currentAccounts.isEmpty() || currentAccounts != null 진입");
            accountRepository.deleteByProductId(invitation.getId());
        }

        if (newAccounts != null) {
            log.info("newAccounts != null 진입");
            List<AccountInfoDto> brideAccountInfo = newAccounts.getBride();
            List<AccountInfoDto> groomAccountInfo = newAccounts.getGroom();

            if (brideAccountInfo != null) {
                log.info("brideAccountInfo != null");
                brideAccountInfo.stream()
                        .map(ba -> {
                            Account account = new Account(ba.getName(), ba.getBankName(), ba.getAccountNumber(), "Bride");
                            account.register(invitation);
                            return account;
                        });
            }
            if (groomAccountInfo != null) {
                log.info("groomAccountInfo != null");
                groomAccountInfo.stream()
                        .map(gc -> {
                            Account account = new Account(gc.getName(), gc.getBankName(), gc.getAccountNumber(), "Groom");
                            account.register(invitation);
                            return account;
                        }).collect(Collectors.toList());
            }

        }
    }
}
