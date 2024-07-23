package jun.invitation.domain.priority.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Getter
public class Priority {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "priority_id")
    private Long id;

    private String name;
    private Integer priority;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
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
