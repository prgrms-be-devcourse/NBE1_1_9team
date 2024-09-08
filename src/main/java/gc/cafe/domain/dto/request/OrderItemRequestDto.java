package gc.cafe.domain.dto.request;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemRequestDto {
    private Long productId;
    private String category;
    private Long price;
    private int quantity;

    public OrderItem toEntity() {
        return OrderItem.builder()
                .category(Category.valueOf(category.toUpperCase()))
                .price(price)
                .quantity(quantity)
                .build();
    }
}
