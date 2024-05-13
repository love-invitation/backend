package jun.invitation.domain.productInfo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductInfoResDto {
    private String title;
    private String details;
    private String groomName;
    private String brideName;
    private List<ProductInfoDto> productInfoList;

    public ProductInfoResDto( List<ProductInfoDto> productInfoList) {
        this.title = "우리 결혼합니다.";
        this.details = "2025.10.20.토요일 오후 2 \n 메종 드 프라이어 그랜드홀";
        this.groomName = "도레미";
        this.brideName = "파솔라";
        this.productInfoList = productInfoList;
    }
}
