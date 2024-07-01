package jun.invitation.domain.productInfo.api;

import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.productInfo.dto.ProductInfoDto;
import jun.invitation.domain.productInfo.dto.ProductInfoResDto;
import jun.invitation.domain.productInfo.service.ProductInfoService;
import jun.invitation.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-infos")
public class ProductInfoController {

    private final ProductInfoService productInfoService;

    @GetMapping
    public ResponseEntity<ResponseDto> getProductByProductInfo() {

        List<ProductInfoDto> productInfoDtos = productInfoService.readAllProductInfos();

        ProductInfoResDto productInfoResDto = new ProductInfoResDto(productInfoDtos, null);

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(productInfoResDto)
                .message("success")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/best")
    public ResponseEntity<ResponseDto> handleBestProductInfos() {
        List<ProductInfoDto> productInfoDtos = productInfoService.readBestProductInfos();

        ProductInfoResDto productInfoResDto = new ProductInfoResDto(productInfoDtos, null);

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(productInfoResDto)
                .message("success.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/{productInfoId}")
    public ResponseEntity<ResponseDto> handleProductInfo(@PathVariable(name = "productInfoId") Long productInfoId) {

        ProductInfo byId = productInfoService.read(productInfoId);

        ProductInfoDto productInfoDto = new ProductInfoDto(byId);

        ProductInfoResDto productInfoResDto = new ProductInfoResDto(null, productInfoDto);

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(productInfoResDto)
                .message("success.")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

}