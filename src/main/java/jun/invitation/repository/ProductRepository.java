package jun.invitation.repository;

import jun.invitation.domain.product.Product;
import jun.invitation.domain.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
