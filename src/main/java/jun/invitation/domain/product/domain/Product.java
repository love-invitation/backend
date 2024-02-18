package jun.invitation.domain.product.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.productInfo.domain.ProductInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private ProductInfo productInfo;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orders_id")
//    private Orders orders;

    // TODO review mapping 해야함 ...
//    private List<Review> reviews;

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

}
