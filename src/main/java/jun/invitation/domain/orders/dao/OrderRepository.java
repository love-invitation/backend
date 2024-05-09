package jun.invitation.domain.orders.dao;

import jun.invitation.domain.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser_id(Long id);
    Optional<Orders> findByProduct_id(Long id);
}
