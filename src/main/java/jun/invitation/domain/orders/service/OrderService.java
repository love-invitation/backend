package jun.invitation.domain.orders.service;

import jakarta.transaction.Transactional;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.orders.dao.OrderRepository;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.dto.OrderDto;
import jun.invitation.domain.orders.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public void requestOrder(Invitation invitation) {
        Orders orders = Orders.builder()
                .user(
//                        SecurityUtils.getCurrentUser()
                        null
                )
                .product(invitation)
                .build();

        orderRepository.save(orders);
    }

    public List<OrderDto> requestOrderDtoList(Long userId) {

        List<Orders> ordersList = orderRepository.findByUser_id(userId);

        return orderRepository.findByUserId(userId).stream().map(OrderDto::new).toList();
    }

    public Orders requestFindOrder(Long id) {
        return orderRepository.findByProduct_id(id).orElseThrow(OrderNotFoundException::new);
    }

    public void delete(Long invitationId) {
        orderRepository.deleteByProductId(invitationId);
    }
}
