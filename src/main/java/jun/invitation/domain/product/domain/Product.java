package jun.invitation.domain.product.domain;

import jakarta.persistence.*;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.user.domain.User;
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
    @JoinColumn(name = "productInfo_id")
    private ProductInfo productInfo;

    private boolean isPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void registerUserProductInfo(User user, ProductInfo productInfo) {
        this.user = user;
        this.productInfo = productInfo;
    }

    public Product(ProductInfo productInfo, User user) {
        this.productInfo = productInfo;
        this.user = user;
        this.isPaid = false;
    }
}
