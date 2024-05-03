package jun.invitation.domain.guestbook.dto;

import lombok.Data;

import java.util.List;

@Data
public class GuestbookListDto {
    private Integer priority;
    private List<GuestbookResponseDto> guestbookList;

    public GuestbookListDto(List<GuestbookResponseDto> responseDtoList, Integer priority) {
        this.priority = priority;
        this.guestbookList = responseDtoList;
    }
}
