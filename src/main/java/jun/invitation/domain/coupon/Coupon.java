package jun.invitation.domain.coupon;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor @Entity
@DiscriminatorColumn(name = "Coupon_Type")
public class Coupon {

    @Id @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private String name;
    private int percent;
    private LocalDateTime expiration_date;

    public Coupon(String name, int percent, LocalDateTime expiration_date) {
        this.name = name;
        this.percent = percent;
        this.expiration_date = expiration_date;
    }
}
