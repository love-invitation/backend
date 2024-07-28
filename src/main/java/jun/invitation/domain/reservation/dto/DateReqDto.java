package jun.invitation.domain.reservation.dto;

import jun.invitation.domain.reservation.domain.DateType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DateReqDto {
    private LocalDateTime date;
    private DateType dateType;

    public DateReqDto(LocalDateTime date, DateType dateType) {
        this.date = date;
        this.dateType = dateType;
    }
}
