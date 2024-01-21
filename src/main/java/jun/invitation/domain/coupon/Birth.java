package jun.invitation.domain.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Birth")
@NoArgsConstructor
public class Birth extends Coupon{
    public Birth(String name, int percent, LocalDateTime expiration_date) {
        super(name, percent, expiration_date);
    }
}
