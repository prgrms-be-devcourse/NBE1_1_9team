package gc.cafe.api.service.order;


import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.request.OrdersByEmailServiceRequest;
import gc.cafe.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse createOrder(OrderCreateServiceRequest request) {
        return null;
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
