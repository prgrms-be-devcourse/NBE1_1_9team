package gc.cafe.service;

import gc.cafe.domain.dto.request.OrderRequestDto;
import gc.cafe.domain.entity.Order;
import gc.cafe.domain.entity.OrderItem;
import gc.cafe.domain.entity.Product;
import gc.cafe.exception.AppException;
import gc.cafe.exception.ErrorCode;
import gc.cafe.repository.OrderRepository;
import gc.cafe.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long createOrder(OrderRequestDto requestDto) {
        Order order = requestDto.toEntity();
        requestDto.getOrderItems()
                .forEach(itemRequestDto -> {
                    Product product = productRepository.findByProductId(itemRequestDto.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
                    OrderItem orderItem = itemRequestDto.toEntity();
                    product.addOrderItem(orderItem);
                    order.addOrderItem(orderItem);
                });
        return orderRepository.save(order).getOrderId();
    }

    public Order readOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));
    }

    public List<Order> readByEmail(String email) {
        return orderRepository.findByEmail(email);
    }
    @Scheduled(cron = "0 0 14 * * ?")
    @Transactional
    public void updateOrderStatus(){
        int updatedRows = orderRepository.bulkUpdateOrderStatus();
    }
}
