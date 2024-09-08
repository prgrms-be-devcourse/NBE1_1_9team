package gc.cafe.api.service.order;


import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.request.OrdersByEmailServiceRequest;
import gc.cafe.api.service.order.response.OrderResponse;
import gc.cafe.domain.order.Order;
import gc.cafe.domain.order.OrderRepository;
import gc.cafe.domain.order.OrderStatus;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Override
    public OrderResponse createOrder(OrderCreateServiceRequest request) {
        Set<Long> productIds = request.getOrderProducts().keySet();
        List<Product> products = productRepository.findAllById(productIds);
        Order order = Order.builder()
            .email(request.getEmail())
            .address(request.getAddress())
            .postcode(request.getPostcode())
            .orderProducts(request.getOrderProducts())
            .products(products)
            .build();
        Order savedOrder = orderRepository.save(order);
        return OrderResponse.of(savedOrder);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public List<OrderResponse> getOrdersByEmail(String email) {
        return List.of();
    }
}
