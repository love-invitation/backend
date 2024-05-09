package jun.invitation.domain.orders.dto;

import jun.invitation.domain.orders.domain.Orders;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productInfoName;
    private String productInfoImageUrl;
    private Boolean isPaid;
    private LocalDateTime lastModified;

    public OrderDto(Orders orders) {
        this.productInfoName = orders.getProduct().getProductInfo().getName();
        // TODO : 이미지 경로 추가 해야함
//        this.productInfoImageUrl = orders.getProduct().getProductInfo();
        this.isPaid = orders.getIsPaid();
        this.lastModified = orders.getProduct().getUpdated_At();
    }
}
