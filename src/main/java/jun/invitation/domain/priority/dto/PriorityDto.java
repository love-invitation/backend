package jun.invitation.domain.priority.dto;

import jun.invitation.domain.priority.domain.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PriorityDto {
    private String name;
    private Integer priority;
}
