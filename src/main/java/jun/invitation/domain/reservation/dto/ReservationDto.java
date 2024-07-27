package jun.invitation.domain.reservation.dto;

import jun.invitation.domain.reservation.domain.DateType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDto {
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
    private DateType dateType;

    public ReservationDto(String placeName, String detail, String placeAddress, Double latitude, Double longitude, LocalDateTime date, DateType dateType) {
        this.placeName = placeName;
        this.detail = detail;
        this.placeAddress = placeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.dateType = dateType;
    }
}
