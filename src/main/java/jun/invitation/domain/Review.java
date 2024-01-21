package jun.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.Product;
import jun.invitation.domain.product.ProductCategory;

@Entity
public class Review {

    @Id @GeneratedValue
    private Long id;

    private String title;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // TODO : 정상 작동하는가 test 해봐야 함


    public Review(String title, String contents, ProductCategory productCategory, User user) {
        this.title = title;
        this.contents = contents;
        this.productCategory = productCategory;
        this.user = user;
    }
}
