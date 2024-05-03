package jun.invitation.domain.guestbook.dto;

import jun.invitation.domain.guestbook.domain.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestbookResponseDto {
    private String name;
    private String message;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.name = guestbook.getName();
        this.message = guestbook.getMessage();
    }
}
