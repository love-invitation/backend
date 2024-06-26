package jun.invitation.domain.productInfo.dto;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static lombok.AccessLevel.PROTECTED;

@Data @AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ProductInfoDto {
    private Long id;
    private String imageUrl;
    private String templateName;
    private BigDecimal price;
    private BigDecimal discountedPrice;

    private Boolean best;
    private Boolean newest;

    public ProductInfoDto(ProductInfo productInfo) {
        this.id = productInfo.getId();
        this.imageUrl = productInfo.getImageUrl();
        this.templateName = productInfo.getTemplateName();
        this.price = productInfo.getPrice();
        this.best = productInfo.getBest();
        this.newest = productInfo.getNewest();
        this.discountedPrice = productInfo.getPrice().multiply(new BigDecimal(0.8)).setScale(2,RoundingMode.HALF_UP);
    }
}
