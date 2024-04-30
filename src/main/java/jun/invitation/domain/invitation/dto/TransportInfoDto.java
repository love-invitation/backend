package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.Transport;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransportInfoDto {
    private Integer priority;
    private List<TransportDto> transport = new ArrayList<>();

    public TransportInfoDto(List<Transport> transports, Integer priority) {
        this.priority = priority;
        toTransportDto(transports);
    }

    private void toTransportDto(List<Transport> transports) {
        for (Transport t : transports) {
            this.transport.add(new TransportDto(t));
        }
    }
}
