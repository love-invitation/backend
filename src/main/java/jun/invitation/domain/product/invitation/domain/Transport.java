package jun.invitation.domain.product.invitation.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Transport {

    @Id @Column(name = "transport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String kind;
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;
}
