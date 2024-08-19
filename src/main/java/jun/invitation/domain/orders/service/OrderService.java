package jun.invitation.domain.orders.service;

import jakarta.transaction.Transactional;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.orders.dao.OrderRepository;
import jun.invitation.domain.orders.domain.Orders;
import jun.invitation.domain.orders.dto.OrderDto;
import jun.invitation.domain.orders.exception.OrderNotFoundException;
import jun.invitation.domain.product.service.ProductService;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.shareThumbnail.dto.ShareThumbnailResDto;
import jun.invitation.domain.shareThumbnail.service.ShareThumbnailService;
import jun.invitation.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShareThumbnailService shareThumbnailService;


    public void create(Invitation invitation) {
        Orders orders = Orders.builder()
                .user(SecurityUtils.getCurrentUser())
                .product(invitation)
                .build();

        orderRepository.save(orders);
    }

    public List<OrderDto> findOrderDtoList(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(o -> createOrderDto(o,userId) )
                .toList();
    }

    private OrderDto createOrderDto(Orders order, Long userId) {
        ShareThumbnail thumbnail = shareThumbnailService.findThumbnail(userId);
        ShareThumbnailResDto shareThumbnailResDto = new ShareThumbnailResDto(thumbnail);
        return new OrderDto(order, shareThumbnailResDto);
    }

    public Orders findOrder(Long id) {
        return orderRepository.findByProduct_id(id)
                .orElseThrow(OrderNotFoundException::new);
    }

    public void delete(Long invitationId) {
        orderRepository.deleteByProductId(invitationId);
    }
}
