package jun.invitation.domain.priority.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.priority.dto.PriorityDto;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "priority_id")
    private Long id;

    private String name;
    private Integer priority;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    public Priority(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public void register(Invitation invitation){
        if (this.invitation != null) {
            this.invitation.getPriority().remove(this);
        }
        this.invitation = invitation;
        invitation.getPriority().add(this);
    }

    public void updatePriority(Integer priority){
        this.priority = priority;
    }
}
