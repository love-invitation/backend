package jun.invitation.domain.productInfo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class ProductInfoResDto {
    private final String title = "우리 결혼합니다.";
    private final String details = "2025.10.20.토요일 오후 2시 \n 메종 드 프라이어 그랜드홀";
    private final String groomName = "도레미";
    private final String brideName = "파솔라";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductInfoDto> productInfoList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ProductInfoDto productInfo;

    public ProductInfoResDto(List<ProductInfoDto> productInfoList, ProductInfoDto productInfo) {
        this.productInfoList = productInfoList;
        this.productInfo = productInfo;
    }


}
