package jun.invitation.domain.guestbook.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDto {
    private String name;
    private String password;
    private String message;
}
