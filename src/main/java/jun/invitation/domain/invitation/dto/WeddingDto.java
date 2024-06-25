package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.WeddingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WeddingDto {
    /**
     * booking : date, date type,
     * place : name, detail, address, latitude, longitude;
     */
    private String placeName;
    private String detail;
    private String placeAddress;

    private Double latitude;
    private Double longitude;

    private LocalDateTime date;
    private WeddingType dateType;

    public WeddingDto(String placeName, String detail, String placeAddress, Double latitude, Double longitude, LocalDateTime date, WeddingType dateType) {
        this.placeName = placeName;
        this.detail = detail;
        this.placeAddress = placeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.dateType = dateType;
    }
}
