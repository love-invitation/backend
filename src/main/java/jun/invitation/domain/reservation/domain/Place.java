package jun.invitation.domain.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Place {

    private String placeName;
    private String detail;
    private String placeAddress;

    @Column(columnDefinition = "GEOMETRY")
    private Point geography;
}
