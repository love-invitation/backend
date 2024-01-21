package jun.invitation.domain.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Recommend")
@NoArgsConstructor
public class Recommend extends Coupon {
    private String who;

    public Recommend(String name, int percent, LocalDateTime expiration_date, String who) {
        super(name, percent, expiration_date);
        this.who = who;
    }
}