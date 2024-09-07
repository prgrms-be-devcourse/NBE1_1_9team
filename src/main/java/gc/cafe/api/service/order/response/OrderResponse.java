package gc.cafe.api.service.order.response;

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
}
