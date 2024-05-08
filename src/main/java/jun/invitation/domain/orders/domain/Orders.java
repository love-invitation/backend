package jun.invitation.domain.orders.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.entity.BaseEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@NoArgsConstructor
@Entity(name = "Orders")
public class Orders extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "product_id" )
    private Product product;

    @Builder
    public Orders(User user, Product product) {
        this.user = user;
        this.product = product;
    }
}
