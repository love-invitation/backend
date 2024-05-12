package jun.invitation.domain.orders.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.orders.domain.Orders;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productInfoName;
    private String productInfoImageUrl;
    private Long tsid;
    private Boolean isPaid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModified;

    public OrderDto(Orders orders) {
        this.productInfoName = orders.getProduct().getProductInfo().getName();
        // TODO : 이미지 경로 추가 해야함
//        this.productInfoImageUrl = orders.getProduct().getProductInfo();
        this.isPaid = orders.getIsPaid();
        this.lastModified = orders.getProduct().getUpdated_At();
        this.tsid = orders.getProduct().getTsid();
    }
}
