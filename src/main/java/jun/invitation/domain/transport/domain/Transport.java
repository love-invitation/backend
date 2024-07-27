package jun.invitation.domain.transport.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.transport.dto.TransportDto;
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
    @JoinColumn(name = "product_id")
    private Product product;

    public Transport(TransportDto transportDto) {
        this.kind = transportDto.getKind();
        this.detail = transportDto.getDetail();
    }

    public void register(Product product) {

        if (this.product != null) {
            this.product.getTransport().remove(this);
        }

        this.product = product;
        product.getTransport().add(this);
    }
}
