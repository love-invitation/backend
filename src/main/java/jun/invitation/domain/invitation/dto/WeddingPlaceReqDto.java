package jun.invitation.domain.invitation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeddingPlaceReqDto {
    private String name;
    private String detail;
    private String address;

    private Double longitude;
    private Double latitude;

    public WeddingPlaceReqDto(String name, String detail, String address, Double longitude, Double latitude) {
        this.name = name;
        this.detail = detail;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
