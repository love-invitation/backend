package jun.invitation.domain.reservation.dto;

import jun.invitation.domain.reservation.domain.Reservation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@RequiredArgsConstructor
public class PlaceDto {
    private Integer priority;
    private String name;
    private String detail;
    private String address;

    private Double longitude;
    private Double latitude;

    public PlaceDto(Reservation reservation, Integer priority) {

        this.priority = priority;

        if (reservation != null){
            this.name = reservation.getPlaceName();
            this.detail = reservation.getDetail();
            this.address = reservation.getPlaceAddress();

            Point geography = reservation.getGeography();
            if (geography != null) {
                this.longitude = geography.getX();
                this.latitude = geography.getY();
            }
        }
    }
}
