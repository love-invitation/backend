package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.Wedding;
import lombok.Data;
import org.locationtech.jts.geom.Point;

@Data
public class WeddingPlaceDto {
    private Integer priority;
    private String placeName;
    private String detail;
    private String placeAddress;

    private Double longitude;
    private Double latitude;

    public WeddingPlaceDto(Wedding wedding, Integer priority) {

        this.priority = priority;

        if (wedding != null){
            this.placeName = wedding.getPlaceName();
            this.detail = wedding.getDetail();
            this.placeAddress = wedding.getPlaceAddress();

            Point geography = wedding.getGeography();
            if (geography != null) {
                this.longitude = geography.getX();
                this.latitude = geography.getY();
            }
        }
    }
}
