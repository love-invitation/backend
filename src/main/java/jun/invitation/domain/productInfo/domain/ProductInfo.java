package jun.invitation.domain.productInfo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "product_info")
public class ProductInfo {


    @GeneratedValue(strategy = IDENTITY)
    @Id
    @Column(name = "productInfo_id")
    private Long id;

    private String imageUrl;

    private String templateName;
    private BigDecimal price;

    private Boolean best;
    private Boolean newest;


    public ProductInfo(String imageUrl, String templateName, BigDecimal price) {
        this.imageUrl = imageUrl;
        this.templateName = templateName;
        this.price = price;
        this.best = false;
        this.newest = false;
    }

    public ProductInfo(String imageUrl, String templateName, BigDecimal price, Boolean best, Boolean newest) {
        this.imageUrl = imageUrl;
        this.templateName = templateName;
        this.price = price;
        this.best = best;
        this.newest = newest;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", templateName='" + templateName + '\'' +
                ", price=" + price +
                ", best=" + best +
                ", newest=" + newest +
                '}';
    }
}
