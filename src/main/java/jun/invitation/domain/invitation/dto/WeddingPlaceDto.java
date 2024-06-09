package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.Wedding;
import lombok.Data;

@Data
public class WeddingPlaceDto {
    private Integer priority;
    private String placeName;
    private String detail;
    private String placeAddress;

    public WeddingPlaceDto(Wedding wedding, Integer priority) {

        this.priority = priority;

        if (wedding != null){
            this.placeName = wedding.getPlaceName();
            this.detail = wedding.getDetail();
            this.placeAddress = wedding.getPlaceAddress();
        }
    }
}
