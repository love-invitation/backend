package jun.invitation.domain.orders.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.orders.dao.OrderRepository;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.dto.OrderDto;
import jun.invitation.domain.orders.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager em;

    public void requestOrder(Invitation invitation) {
        Orders orders = Orders.builder()
                .user(
//                        SecurityUtils.getCurrentUser(),
                        null
                )
                .product(invitation)
                .build();

        orderRepository.save(orders);
    }

    public List<OrderDto> requestOrderDtoList(Long userId) {
        List<Orders> ordersList = orderRepository.findByUser_id(userId);

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Orders orders : ordersList) {
            orderDtoList.add(new OrderDto(orders));
        }

        return orderDtoList;
    }

    public Orders requestFindOrder(Long id) {
        return orderRepository.findByProduct_id(id).orElseThrow(OrderNotFoundException::new);
    }

    public void delete(Long invitationId) {
        orderRepository.deleteByProductId(invitationId);
    }
}
