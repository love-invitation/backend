package jun.invitation.domain.priority.dto;

import jun.invitation.domain.priority.domain.Priority;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriorityDto {
    private String name;
    private Integer priority;
}
