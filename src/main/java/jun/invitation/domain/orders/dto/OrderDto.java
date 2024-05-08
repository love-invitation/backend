package jun.invitation.domain.orders.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productName;
    private Boolean isPaid;
    private LocalDateTime lastModified;
}
