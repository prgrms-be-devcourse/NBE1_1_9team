package gc.cafe.domain.dto.request;

import gc.cafe.domain.entity.Order;
import gc.cafe.domain.entity.OrderItem;
import gc.cafe.domain.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
    private String email;
    private String address;
    private String postcode;
    private List<OrderItemRequestDto> orderItems;

    public Order toEntity() {
        return Order.builder()
                .email(email)
                .address(address)
                .postcode(postcode)
                .orderStatus(OrderStatus.READY)
                .build();
    }

}
