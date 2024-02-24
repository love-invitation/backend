package jun.invitation.domain.product.dao;

import jun.invitation.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
