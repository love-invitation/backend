package jun.invitation.domain.productInfo.service;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.dao.ProductInfoRepository;
import jun.invitation.domain.productInfo.dto.ProductInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ProductInfoDto> requestBestProductInfos() {

        List<ProductInfo> byBestTrue = productInfoRepository.findByBestTrue();

        List<ProductInfoDto> productInfoDtos = new ArrayList<>();
        for (ProductInfo productInfo : byBestTrue) {
            productInfoDtos.add(new ProductInfoDto(productInfo.getImageUrl(),productInfo.getName(), productInfo.getPrice()));
        }

        return productInfoDtos;
    }
}
