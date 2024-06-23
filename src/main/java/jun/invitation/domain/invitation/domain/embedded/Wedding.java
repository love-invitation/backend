package jun.invitation.domain.invitation.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wedding {

    private String placeName;
    private String detail;
    private String placeAddress;

    private Double latitude;
    private Double longitude;

    private LocalDateTime date;
    private String dateType;
}
