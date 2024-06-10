package jun.invitation.domain.transport.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.dto.TransportDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transport {

    @Id
    @Column(name = "transport_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String kind;
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    public Transport(TransportDto transportDto) {
        this.kind = transportDto.getKind();
        this.detail = transportDto.getDetail();
    }

    public void register(Invitation invitation) {
        this.invitation = invitation;
        invitation.getTransport().add(this);
    }
}
