package jun.invitation.domain.orders.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productInfoName;
    private String productInfoImageUrl;
    private Long tsid;
    private Boolean isPaid;

    private ShareThumbnailResDto thumbnail;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModified;

    public OrderDto(Orders orders, ShareThumbnailResDto thumbnail) {
        this.productInfoName = orders.getProduct().getProductInfo().getTemplateName();
        this.productInfoImageUrl = orders.getProduct().getProductInfo().getImageUrl();
        this.thumbnail = thumbnail;
        this.isPaid = orders.getIsPaid();
        this.lastModified = orders.getProduct().getUpdated_At();
        this.tsid = orders.getProduct().getTsid();
    }
}
