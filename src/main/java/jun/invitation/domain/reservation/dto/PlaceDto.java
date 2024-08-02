package jun.invitation.domain.reservation.dto;

import jun.invitation.domain.reservation.domain.Place;
import jun.invitation.domain.reservation.domain.Reservation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.Optional;

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

        Optional.ofNullable(reservation)
                .map(Reservation::getPlace)
                .ifPresent(this::init);
    }

    private void init(Place p) {
        this.name = p.getPlaceName();
        this.detail = p.getDetail();
        this.address = p.getPlaceAddress();
        Optional.ofNullable(p.getGeography())
                .ifPresent(g-> {
                    this.longitude = g.getX();
                    this.latitude = g.getY();
                });
    }
}
