package jun.invitation.api;

import jun.invitation.domain.product.ProductInfo;
import jun.invitation.service.ProductInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductInfoService productInfoService;

    @GetMapping("/api/product/category")
    public Result getProductByCategory() {

        List<ProductInfo> productInfoList = productInfoService.allProductCategory();
        List<ProductInfoDto> result = productInfoList.stream()
                .map(pl -> new ProductInfoDto(pl.getId(), pl.getName(), pl.getPrice()))
                .collect(Collectors.toList());

        return new Result(result);
    }

    @Data
    @AllArgsConstructor
    static class ProductInfoDto {
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }


}
