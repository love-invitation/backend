package jun.invitation.repository;

import jun.invitation.domain.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    public ProductCategory findByName(String name);
}
