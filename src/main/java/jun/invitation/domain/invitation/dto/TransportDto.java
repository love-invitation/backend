package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.Transport;
import lombok.Data;

@Data
public class TransportDto {
    private String kind;
    private String detail;

    public TransportDto(Transport transport) {
        this.kind = transport.getKind();
        this.detail = transport.getDetail();
    }
}
