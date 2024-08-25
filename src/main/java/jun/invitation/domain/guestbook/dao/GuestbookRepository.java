package jun.invitation.domain.guestbook.dao;

import jun.invitation.domain.guestbook.domain.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>{

    @Modifying
    @Query(value = "delete from Guestbook g where g.product.id = :id")
    void deleteByProductId(@Param(value = "id") Long productId);

    Page<Guestbook> findByProductTsidOrderByIdDesc(Long productTsid, Pageable pageable);
}
