package gc.cafe.api.service.order.response;

import gc.cafe.domain.order.Order;
import gc.cafe.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderResponse {

    private Long id;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private List<OrderDetailResponse> orderDetails;

    @Builder
    public OrderResponse(Long id, String email, String address, String postcode, OrderStatus orderStatus, List<OrderDetailResponse> orderDetails) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .email(order.getEmail())
            .address(order.getAddress().getAddress())
            .postcode(order.getAddress().getPostcode())
            .orderStatus(order.getOrderStatus())
            .orderDetails(order.getOrderProducts().stream()
                .map(orderProduct -> OrderDetailResponse.builder()
                    .category(orderProduct.getProduct().getCategory())
                    .price(orderProduct.getProduct().getPrice())
                    .quantity(orderProduct.getQuantity())
                    .build())
                .toList())
            .build();
    }
}
