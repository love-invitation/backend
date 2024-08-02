package jun.invitation.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.reservation.domain.DateType;
import jun.invitation.domain.reservation.domain.Reservation;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class DateDto {
    private Integer priority;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    private DateType dateType;

    public DateDto(Reservation reservation, Integer priority) {
        this.priority = priority;
        Optional.ofNullable(reservation)
                .map(Reservation::getBooking)
                .ifPresent(b -> {
                    this.date = b.getDate();
                    this.dateType = b.getDateType();
                });
    }
}
