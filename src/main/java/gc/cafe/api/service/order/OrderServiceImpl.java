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

    /*
    * TODO : create 메서드가 서비스 레이어에서 사용되는 request를 모르는 상태로 변경 필요
    *  : create 테스트 코드 작성 필요
    * 왜냐하면 패키지간 의존성이 생기기 때문에 서비스 레이어에서 사용되는 request를 모르는 상태로 변경해야 함
    * 서비스 레이어에서 도메인 레이어로 내려 줄 때는 도메인 레이어의 객체를 사용해야 함
    * 근데 인자가 3개 이하인게 좋다고 했음, 근데 이럴 경우엔 어떻게 내려줘야됨 ?
    * request 대신에 여기서 파라미터를 통해 하나씩 만들어서 내려주면 된다고 생각이 드는데 그러면 인자가 너무많아짐
    * Order로 내려준다? 그러면 굳이 create를 만들 필요가있나 ?
    * */
    @Override
    public OrderResponse createOrder(OrderCreateServiceRequest request) {
        Set<Long> productIds = request.getOrderProducts().keySet();
        List<Product> products = productRepository.findAllById(productIds);
        Order order = Order.create(request,products);
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
