package jun.invitation.domain.guestbook.dto;

import jun.invitation.domain.guestbook.domain.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GuestbookResponseDto {
    private Long id;
    private String name;
    private String message;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.id = guestbook.getId();
        this.name = guestbook.getName();
        this.message = guestbook.getMessage();
    }
}
