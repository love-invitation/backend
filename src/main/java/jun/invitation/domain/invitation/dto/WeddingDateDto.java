package jun.invitation.domain.invitation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.domain.invitation.domain.embedded.WeddingType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeddingDateDto {
    private Integer priority;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    private WeddingType dateType;

    public WeddingDateDto(Wedding wedding, Integer priority) {
        this.priority = priority;
        if (wedding != null) {
            this.date = wedding.getDate();
            this.dateType = wedding.getDateType();
        }
    }
}
