package jun.invitation.domain.invitation.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wedding {

    private String placeName;
    private String detail;
    private String placeAddress;

    @Column(columnDefinition = "GEOMETRY")
    private Point geography;

    private LocalDateTime date;

    @Enumerated(value = STRING)
    private WeddingType dateType;
}
