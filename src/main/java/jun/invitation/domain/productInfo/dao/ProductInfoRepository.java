package jun.invitation.domain.productInfo.dao;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    ProductInfo findByName(String name);
    List<ProductInfo> findByBestTrue();
}
