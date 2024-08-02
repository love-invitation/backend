package jun.invitation.domain.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.*;

@NoArgsConstructor
@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Embedded
    private Place place;

    @Embedded
    private Booking booking;

    public Reservation(Place place, Booking booking) {
        this.place = place;
        this.booking = booking;
    }

    public void updatePlace(Place place) {
        this.place = place;
    }

    public void updateBooking(Booking booking) {
        this.booking = booking;
    }
}
