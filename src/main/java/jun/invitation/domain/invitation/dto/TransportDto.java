package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.transport.domain.Transport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportDto {
    private String kind;
    private String detail;

    public TransportDto(Transport transport) {
        this.kind = transport.getKind();
        this.detail = transport.getDetail();
    }
}
