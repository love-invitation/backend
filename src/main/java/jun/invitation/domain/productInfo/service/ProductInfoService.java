package jun.invitation.domain.productInfo.service;

import jun.invitation.domain.productInfo.dao.ProductInfoRepository;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.dto.ProductInfoDto;
import jun.invitation.domain.productInfo.exception.ProductInfoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    public void save(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }

    @Transactional(readOnly = true)
    public List<ProductInfoDto> findAll() {
        return productInfoRepository.findAll()
                .stream().map(ProductInfoDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductInfo findById(Long id) {
        return productInfoRepository.findById(id)
                .orElseThrow(ProductInfoNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<ProductInfoDto> findByBestList() {
        return productInfoRepository.findByBestTrue()
                .stream().map(ProductInfoDto::new)
                .collect(Collectors.toList());
    }
}
