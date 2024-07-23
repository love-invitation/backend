package jun.invitation.domain.orders.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "orders")
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

    private Boolean isPaid;

    @Builder
    public Orders(User user, Product product) {
        this.user = user;
        this.product = product;
        this.isPaid = false;
    }
}
