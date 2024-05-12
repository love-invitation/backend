package jun.invitation.domain.productInfo.api;

import jun.invitation.domain.productInfo.dto.ProductInfoDto;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductInfoController {

    private final ProductInfoService productInfoService;

    @GetMapping("/api/product/info")
    public ResponseEntity<ResponseDto> getProductByProductInfo() {

        List<ProductInfoDto> productInfoDtos = productInfoService.allProductCategory();

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(productInfoDtos)
                .message("success")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/api/product/info/best")
    public ResponseEntity<ResponseDto> handleBestProductInfos() {
        List<ProductInfoDto> productInfoDtos = productInfoService.requestBestProductInfos();

        ResponseDto<Object> result = ResponseDto.builder()
                .result(productInfoDtos)
                .message("success.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

}
