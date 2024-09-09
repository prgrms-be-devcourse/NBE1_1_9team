package gc.cafe.domain.dto.response;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long seq;
    private Long productId;
    private Category category;
    private Long price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderItemResponseDto(OrderItem orderItem){
        this.seq = orderItem.getSeq();
        this.productId = orderItem.getProduct().getProductId();
        this.category = orderItem.getCategory();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
        this.createdAt = orderItem.getCreatedAt();
        this.updatedAt = orderItem.getUpdatedAt();
    }
}
