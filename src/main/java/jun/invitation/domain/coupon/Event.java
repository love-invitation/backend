package jun.invitation.domain.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Event")
@NoArgsConstructor
public class Event extends Coupon {
}
