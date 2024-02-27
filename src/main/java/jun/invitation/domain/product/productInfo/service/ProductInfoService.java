package jun.invitation.domain.product.productInfo.service;

import jun.invitation.domain.product.productInfo.domain.ProductInfo;
import jun.invitation.domain.product.productInfo.dao.ProductInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
        return;
    }

    public List<ProductInfo> allProductCategory() {
        return productInfoRepository.findAll();
    }

    public ProductInfo findOne(String name) {

        return productInfoRepository.findByName(name);
    }

    public Optional<ProductInfo> findById(Long id) {
        return productInfoRepository.findById(id);
    }
}
