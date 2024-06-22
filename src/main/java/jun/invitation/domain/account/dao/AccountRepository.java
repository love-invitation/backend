package jun.invitation.domain.account.dao;

import jun.invitation.domain.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query(value = "delete from Account a where a.invitation.id = :id")
    void deleteByProductId(@Param(value = "id") Long productId);
}
