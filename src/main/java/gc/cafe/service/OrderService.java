package gc.cafe.service;

import gc.cafe.domain.dto.request.OrderRequestDto;
import gc.cafe.domain.entity.Order;
import gc.cafe.domain.entity.OrderItem;
import gc.cafe.domain.entity.OrderStatus;
import gc.cafe.domain.entity.Product;
import gc.cafe.exception.AppException;
import gc.cafe.exception.ErrorCode;
import gc.cafe.repository.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Long create(OrderRequestDto requestDto) {
        Order order = requestDto.toEntity();
        requestDto.getOrderItems()
                .forEach(itemRequestDto -> {
                    Product product = productRepository.findById(itemRequestDto.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
                    OrderItem orderItem = itemRequestDto.toEntity();
                    product.addOrderItem(orderItem);
                    order.addOrderItem(orderItem);
                });
        return orderRepository.save(order).getOrderId();
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));
    }

    public List<Order> findOrdersByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    @Transactional
    public Long update(Long orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ORDER));
        updateOrderDetails(order, requestDto);
        orderItemRepository.deleteByOrder(order);
        requestDto.getOrderItems()
                .forEach(itemRequestDto -> {
                    Product product = productRepository.findById(itemRequestDto.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
                    OrderItem orderItem = itemRequestDto.toEntity();
                    product.addOrderItem(orderItem);
                    order.addOrderItem(orderItem);
                });
        return orderRepository.save(order).getOrderId();
    }

    private void updateOrderDetails(Order order, OrderRequestDto requestDto) {
        order.update(requestDto.getEmail(),
                requestDto.getAddress(),
                requestDto.getPostcode(),
                OrderStatus.READY);
    }

    @Scheduled(cron = "0 0 14 * * ?")
    @Transactional
    public void updateOrderStatus() {
        orderRepository.bulkUpdateOrderStatus();
    }

}
