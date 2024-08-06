package jun.invitation.domain.account.service;

import jun.invitation.domain.account.dao.AccountRepository;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.account.dto.AccountInfoDto;
import jun.invitation.domain.account.dto.AccountReqDto;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.WeddingSide;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static java.util.stream.Collectors.*;
import static jun.invitation.domain.invitation.domain.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.WeddingSide.GROOM;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;

    public void register(List<AccountInfoDto> accountInfoDtos, Invitation invitation, WeddingSide side) {

        if (ObjectUtils.isEmpty(accountInfoDtos))
            return;

        accountInfoDtos.stream()
                .map(a -> new Account(a.getName(), a.getBankName(), a.getAccountNumber(), side))
                .forEach(account -> account.register(invitation));
    }

    public Map<String, List<AccountInfoDto>> classifyBySide(List<Account> accounts) {
        Map<WeddingSide, List<Account>> collect = accounts.stream()
                .collect(groupingBy(Account::getWeddingSide));

        return collect.entrySet().stream()
                .collect(
                        toMap( entry -> entry.getKey().getSide(),
                                entry -> entry.getValue().stream()
                                        .map(AccountInfoDto::new)
                                        .toList()
                        )
                );
    }

    public void delete(Long productId) {
        accountRepository.deleteByProductId(productId);
    }

    public void update(AccountReqDto newAccounts, Invitation invitation) {

        if (!ObjectUtils.isEmpty(invitation.getAccounts())) {
            accountRepository.deleteByProductId(invitation.getId());
        }

        Optional.ofNullable(newAccounts)
                .ifPresent(update -> {
                    register(update.getGroom(), invitation, GROOM);
                    register(update.getBride(), invitation, BRIDE);
                });
    }

    public void create(AccountReqDto accounts, Invitation invitation) {
        Optional.ofNullable(accounts)
                .ifPresent(a -> {
                    register(a.getGroom(), invitation, GROOM);
                    register(a.getBride(), invitation, BRIDE);
                });
    }
}
