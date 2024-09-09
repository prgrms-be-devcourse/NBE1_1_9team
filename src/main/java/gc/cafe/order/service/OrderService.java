package gc.cafe.order.service;

import gc.cafe.order.dto.EmailVO;
import gc.cafe.order.dto.OrderDetailResponse;
import gc.cafe.order.dto.OrderListResponse;
import gc.cafe.order.dto.OrderProductResponse;
import gc.cafe.order.dto.OrderRequest;
import gc.cafe.order.dto.OrderResponse;
import gc.cafe.order.domain.Order;
import gc.cafe.order.dto.OrderSimpleResponse;
import gc.cafe.order.dto.OrderUpdateRequest;
import gc.cafe.order.repository.OrderRepository;
import gc.cafe.orderitem.domain.OrderItem;
import gc.cafe.orderitem.repository.OrderItemRepository;
import gc.cafe.product.domain.Product;
import gc.cafe.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Scheduled(cron = "0 0 14 * * *")
    public void deliveryAllOrder() {
        String sql = "UPDATE Orders SET order_status = ? WHERE order_status = ?";
        jdbcTemplate.update(sql, "DELIVERED", "ORDERED");
    }

    @Transactional
    public OrderResponse makeOrder(OrderRequest request) {
        Order order = new Order(request.email(), request.address(), request.postcode());
        Order savedOrder = orderRepository.save(order);

        for (OrderProductResponse infos : request.products()) {
            Product product = productRepository.findByProductName(infos.productName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));

            OrderItem orderItem = new OrderItem(infos.quantity(), product, order);
            orderItemRepository.save(orderItem);
        }

        return new OrderResponse(savedOrder.getId());
    }

    public OrderListResponse getOrders(EmailVO email) {
        List<OrderSimpleResponse> result = orderRepository.findByEmail(email.value()).stream()
                .map(it -> new OrderSimpleResponse(it.getId(), it.getEmail(), it.getAddress(), it.getPostcode(), calculateTotalPrice(it)))
                .toList();
        return new OrderListResponse(result);
    }

    private int calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .mapToInt(it -> it.getQuantity() * it.getProduct().getPrice())
                .sum();
    }

    public OrderDetailResponse getOrderDetail(Long orderId) {
        Order order = getOrder(orderId);
        return new OrderDetailResponse(order.getEmail(), order.getAddress(), order.getPostcode(), getProductList(order));
    }

    private List<OrderProductResponse> getProductList(Order order) {
        return order.getOrderItems().stream()
                .map(it -> new OrderProductResponse(it.getProduct().getProductName(), it.getQuantity()))
                .toList();
    }

    @Transactional
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = getOrder(orderId);
        order.update(request);

        for (OrderProductResponse infos : request.products()) {
            Product product = productRepository.findByProductName(infos.productName())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));

            OrderItem orderItem = orderItemRepository.findByOrderIdAndProductId(order.getId(), product.getId())
                    .orElseThrow(() -> new IllegalArgumentException("에러 발생"));
            orderItem.update(infos.quantity());
        }

        return new OrderResponse(order.getId());
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = getOrder(orderId);
        List<OrderItem> orderItems = order.getOrderItems();
        orderItemRepository.deleteAll(orderItems);
        orderRepository.deleteById(orderId);
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findByIdWithOrOrderItems(orderId)
                .orElseThrow(() -> new IllegalArgumentException("해당 주문은 없습니다."));
    }

}
