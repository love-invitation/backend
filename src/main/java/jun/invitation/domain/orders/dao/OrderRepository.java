package jun.invitation.domain.orders.dao;

import jun.invitation.domain.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_id(Long id);
    Optional<Orders> findByProduct_id(Long id);

    @Modifying
    @Query(value = "delete from Orders o where o.product_id = :id", nativeQuery = true)
    void deleteByProductId(@Param("id") Long id);
}
