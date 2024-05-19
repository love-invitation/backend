package jun.invitation.domain.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;


@Data
public class GuestbookListDto {
    private Integer priority;
    private Page<GuestbookResponseDto> guestbookList;

    public GuestbookListDto(Page<GuestbookResponseDto> responseDtoList, Integer priority) {
        this.priority = priority;
        this.guestbookList = responseDtoList;
    }
}
