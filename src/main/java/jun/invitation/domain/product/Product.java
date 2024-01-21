package jun.invitation.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Product_Type")
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orders_id")
//    private Orders orders;

    // TODO review mapping 해야함 ...
//    private List<Review> reviews;

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

}
