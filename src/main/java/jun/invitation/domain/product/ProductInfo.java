package jun.invitation.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class ProductInfo {

    @Id @GeneratedValue
    @Column(name = "productCategory_id")
    private Long id;

    private String name;
    private BigDecimal price;

    public ProductInfo(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
