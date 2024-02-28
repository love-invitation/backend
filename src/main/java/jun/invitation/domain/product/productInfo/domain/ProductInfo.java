package jun.invitation.domain.product.productInfo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class ProductInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productInfo_id")
    private Long id;

    private String name;
    private BigDecimal price;

    public ProductInfo(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
