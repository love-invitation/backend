package jun.invitation.domain.productInfo.dto;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.*;

@Data @AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ProductInfoDto {
    private String imageUrl;
    private String templateName;
    private BigDecimal price;

    private Boolean best;
    private Boolean newest;

    public ProductInfoDto(ProductInfo productInfo) {
        this.imageUrl = productInfo.getImageUrl();
        this.templateName = productInfo.getName();
        this.price = productInfo.getPrice();
        this.best = productInfo.getBest();
        this.newest = productInfo.getNewest();
    }
}
