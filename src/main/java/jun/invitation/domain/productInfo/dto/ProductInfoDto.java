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
    private BigDecimal price = BigDecimal.ZERO;

    private Boolean best;
    private Boolean newest;

    public ProductInfoDto(ProductInfo productInfo) {
        this.id = productInfo.getId();
        this.imageUrl = productInfo.getImageUrl();
        this.templateName = productInfo.getTemplateName();
        this.best = productInfo.getBest();
        this.newest = productInfo.getNewest();
    }
}
