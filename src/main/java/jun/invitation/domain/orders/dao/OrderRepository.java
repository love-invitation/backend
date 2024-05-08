package jun.invitation.domain.orders.dao;

import jun.invitation.domain.orders.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
