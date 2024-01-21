package jun.invitation.domain.coupon;

import jakarta.persistence.*;
import jun.invitation.domain.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class UserCoupon {

    @Id
    @GeneratedValue
    @Column(name = "userCoupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
