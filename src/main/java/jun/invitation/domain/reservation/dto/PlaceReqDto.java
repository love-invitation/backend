package jun.invitation.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceReqDto {
    private String name;
    private String detail;
    private String address;

    private Double longitude;
    private Double latitude;

    public PlaceReqDto(String name, String detail, String address, Double longitude, Double latitude) {
        this.name = name;
        this.detail = detail;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
