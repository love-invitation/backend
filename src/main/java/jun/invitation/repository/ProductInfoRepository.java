package jun.invitation.repository;

import jun.invitation.domain.product.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    public ProductInfo findByName(String name);
}
