package jun.invitation.domain.account.service;

import jun.invitation.domain.account.dao.AccountRepository;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.account.dto.AccountInfoDto;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import jun.invitation.domain.priority.PriorityName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.*;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.embedded.WeddingSide.GROOM;
import static jun.invitation.domain.priority.PriorityName.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public void save(List<AccountInfoDto> accountInfoDtos, Invitation invitation, WeddingSide side) {

        if (accountInfoDtos == null) {
            return;
        }
        accountInfoDtos.stream()
                .map(a -> new Account(a.getName(), a.getBankName(), a.getAccountNumber(), side))
                .forEach(account -> account.register(invitation));
    }

    public Map<String, List> getSeperatedMap(List<Account> accounts) {

        List<AccountInfoDto> groom = new ArrayList<>();
        List<AccountInfoDto> bride = new ArrayList<>();

        accounts.forEach(account -> {
            if (account.getWeddingSide().equals(BRIDE))
                bride.add(
                    new AccountInfoDto(
                            account.getName(),
                            account.getAccountNumber(),
                            account.getBankName()
                    )
            );
            else if (account.getWeddingSide().equals(GROOM))
                groom.add(
                        new AccountInfoDto(
                                account.getName(),
                                account.getAccountNumber(),
                                account.getBankName()
                        )
                );
        });

        Map<String, List> seperatedMap = new HashMap<>();
        seperatedMap.put(BRIDE.getSide(), bride );
        seperatedMap.put(GROOM.getSide(), groom);
        return seperatedMap;

    }

    public void delete(Long productId) {
        accountRepository.deleteByProductId(productId);
    }

    public void update(AccountReqDto newAccounts, List<Account> currentAccounts, Invitation invitation) {

        if (!currentAccounts.isEmpty() || currentAccounts != null) {
            accountRepository.deleteByProductId(invitation.getId());
        }

        if (newAccounts != null) {
            List<AccountInfoDto> brideAccountInfo = newAccounts.getBride();
            List<AccountInfoDto> groomAccountInfo = newAccounts.getGroom();

            if (brideAccountInfo != null) {
                brideAccountInfo.stream()
                        .map(ba -> {
                            Account account = new Account(ba.getName(), ba.getBankName(), ba.getAccountNumber(), BRIDE);
                            account.register(invitation);
                            return account;
                        });
            }
            if (groomAccountInfo != null) {
                groomAccountInfo.stream()
                        .map(gc -> {
                            Account account = new Account(gc.getName(), gc.getBankName(), gc.getAccountNumber(), GROOM);
                            account.register(invitation);
                            return account;
                        }).collect(Collectors.toList());
            }

        }
    }
}
