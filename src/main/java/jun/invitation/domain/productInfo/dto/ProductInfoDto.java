package jun.invitation.domain.productInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data @AllArgsConstructor
public class ProductInfoDto {
    private String imageUrl;
    private String name;
    private BigDecimal price;
}
