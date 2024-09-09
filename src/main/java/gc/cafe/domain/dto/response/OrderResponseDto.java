package gc.cafe.domain.dto.response;

import gc.cafe.domain.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String email;
    private String address;
    private String postcode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemResponseDto> orderItems;

    public OrderResponseDto(Order entity){
        this.orderId = entity.getOrderId();
        this.email = entity.getEmail();
        this.address = entity.getAddress();
        this.postcode = entity.getPostcode();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.orderItems = entity.getOrderItems()
                .stream()
                .map(OrderItemResponseDto::new)
                .toList();
    }

}
