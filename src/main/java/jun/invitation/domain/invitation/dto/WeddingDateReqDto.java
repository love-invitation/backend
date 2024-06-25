package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.embedded.WeddingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WeddingDateReqDto {
    private LocalDateTime date;
    private WeddingType dateType;

    public WeddingDateReqDto(LocalDateTime date, WeddingType dateType) {
        this.date = date;
        this.dateType = dateType;
    }
}
