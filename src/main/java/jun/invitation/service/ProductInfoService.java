package jun.invitation.service;

import jun.invitation.domain.product.ProductInfo;
import jun.invitation.repository.ProductInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
