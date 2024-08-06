package jun.invitation.domain.guestbook.dto;

import jun.invitation.domain.guestbook.domain.Guestbook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDto {
    private String name;
    private String password;
    private String message;
}
