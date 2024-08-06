package jun.invitation.domain.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static jun.invitation.domain.invitation.domain.WeddingSide.BRIDE;
import static jun.invitation.domain.invitation.domain.WeddingSide.GROOM;

@Data
@NoArgsConstructor
public class AccountResDto {

    private Integer priority;

    private List<AccountInfoDto> groom;
    private List<AccountInfoDto> bride;

    public AccountResDto(Map<String, List<AccountInfoDto>> classifiedMap, Integer priority) {

        this.priority = priority;

        this.groom = classifiedMap.get(GROOM.getSide());
        this.bride = classifiedMap.get(BRIDE.getSide());

    }
}
