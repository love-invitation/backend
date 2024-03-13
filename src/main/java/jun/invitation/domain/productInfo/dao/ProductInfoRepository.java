package jun.invitation.domain.productInfo.dao;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    public ProductInfo findByName(String name);

}
