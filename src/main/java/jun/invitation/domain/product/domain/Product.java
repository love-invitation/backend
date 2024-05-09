package jun.invitation.domain.product.domain;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Product_Type")
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productInfo_id")
    private ProductInfo productInfo;

//    private boolean isPaid;

    @Column(nullable = false, unique = true)
    private Long tsid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void register(User user, ProductInfo productInfo) {
        this.user = user;
        this.tsid = TsidCreator.getTsid().toLong();
        this.productInfo = productInfo;
    }

    public Product(ProductInfo productInfo, User user) {
        this.productInfo = productInfo;
        this.user = user;
//        this.isPaid = false;
    }
}
