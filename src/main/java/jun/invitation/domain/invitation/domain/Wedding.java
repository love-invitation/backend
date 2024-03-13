package jun.invitation.domain.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor @Data
public class Wedding {

    private String placeName;
    private String placeInfo;
    private String placeAddress;
    private LocalDateTime date;
    private String dateType;

}
