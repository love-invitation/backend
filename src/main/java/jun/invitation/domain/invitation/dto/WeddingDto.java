package jun.invitation.domain.invitation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WeddingDto {
    private String placeName;
    private String detail;
    private String placeAddress;

    private Double latitude;
    private Double longitude;

    private LocalDateTime date;
    private String dateType;

    public WeddingDto(String placeName, String detail, String placeAddress, Double latitude, Double longitude, LocalDateTime date, String dateType) {
        this.placeName = placeName;
        this.detail = detail;
        this.placeAddress = placeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.dateType = dateType;
    }
}
