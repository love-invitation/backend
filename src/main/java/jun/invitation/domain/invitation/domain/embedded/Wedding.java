package jun.invitation.domain.invitation.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wedding {

    private String placeName;
    private String detail;
    private String placeAddress;
    private LocalDateTime date;
    private String dateType;

}
