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

    private String placeName;
    private String detail;
    private String placeAddress;

    @Column(columnDefinition = "GEOMETRY")
    private Point geography;

    private LocalDateTime date;

    @Enumerated(value = STRING)
    private DateType dateType;

    public Reservation(String placeName, String detail, String placeAddress, Point geography, LocalDateTime date, DateType dateType) {
        this.placeName = placeName;
        this.detail = detail;
        this.placeAddress = placeAddress;
        this.geography = geography;
        this.date = date;
        this.dateType = dateType;
    }

    public void updatePlace(String placeName, String detail, String placeAddress, Point geography) {
        this.placeName = placeName;
        this.detail = detail;
        this.placeAddress = placeAddress;
        this.geography = geography;
    }

    public void updateBooking(LocalDateTime date, DateType dateType) {
        this.date = date;
        this.dateType = dateType;
    }

}
