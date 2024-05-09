package jun.invitation.domain.orders.api;

import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.dto.OrderDto;
import jun.invitation.domain.orders.service.OrderService;
import jun.invitation.global.dto.ResponseDto;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ResponseDto> orderAllList() {

        List<OrderDto> orders = orderService.requestOrderDtoList(SecurityUtils.getCurrentUser().getId());

        ResponseDto<Object> result = ResponseDto.builder()
                .status(HttpStatus.OK.value())
                .result(orders)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }
}
