package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.Wedding;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeddingDateDto {
    private Integer priority;
    private LocalDateTime date;
    private String dateType;

    public WeddingDateDto(Wedding wedding, Integer priority) {
        this.priority = priority;
        this.date = wedding.getDate();
        this.dateType = wedding.getDateType();
    }
}
