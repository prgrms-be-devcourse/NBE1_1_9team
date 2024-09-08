package gc.cafe.domain.dto.response;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long seq;
    private Long productId;
    private Category category;
    private Long price;
    private int quantity;

    public OrderItemResponseDto(OrderItem orderItem){
        this.seq = orderItem.getSeq();
        this.productId = orderItem.getProduct().getProductId();
        this.category = orderItem.getCategory();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }
}
