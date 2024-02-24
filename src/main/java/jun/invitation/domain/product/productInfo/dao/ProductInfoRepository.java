package jun.invitation.domain.product.productInfo.dao;

import jun.invitation.domain.product.productInfo.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    public ProductInfo findByName(String name);
}
