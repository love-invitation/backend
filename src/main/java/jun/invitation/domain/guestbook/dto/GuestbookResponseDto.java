package jun.invitation.domain.guestbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.guestbook.domain.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GuestbookResponseDto {
    private Long id;
    private String name;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.id = guestbook.getId();
        this.name = guestbook.getName();
        this.message = guestbook.getMessage();
        this.created = guestbook.getCreated_At();
    }
}
