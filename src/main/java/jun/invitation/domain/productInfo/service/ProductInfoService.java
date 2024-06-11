package jun.invitation.domain.productInfo.service;

import jun.invitation.domain.productInfo.dao.ProductInfoRepository;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.dto.ProductInfoDto;
import jun.invitation.domain.productInfo.exception.ProductInfoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
        return;
    }

    public List<ProductInfoDto> requestAllProductInfos() {
        List<ProductInfo> productInfoList = productInfoRepository.findAll();

        return productInfoList.stream()
                .map(ProductInfoDto::new)
                .collect(Collectors.toList());
    }

    public ProductInfo findById(Long id) {
        return productInfoRepository.findById(id).orElseThrow(ProductInfoNotFoundException::new);
    }

    public List<ProductInfoDto> requestBestProductInfos() {

        List<ProductInfo> productInfoByBest = productInfoRepository.findByBestTrue();

        return productInfoByBest.stream()
                .map(ProductInfoDto::new)
                .collect(Collectors.toList());
    }
}
