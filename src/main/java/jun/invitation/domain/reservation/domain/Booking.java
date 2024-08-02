package jun.invitation.domain.reservation.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Booking {
    private LocalDateTime date;

    @Enumerated(value = STRING)
    private DateType dateType;
}
