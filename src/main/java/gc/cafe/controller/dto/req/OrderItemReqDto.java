package gc.cafe.controller.dto.req;

import gc.cafe.model.entity.OrderItems;
import gc.cafe.model.entity.Orders;
import gc.cafe.model.entity.Products;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItemReqDto {

    private Long seq;

    @NotNull(message = "유효하지 않은 상품입니다.")
    private String id;

    @Min(value = 1, message = "수량을 최소 1개 이상 입력해주세요")
    @NotNull(message = "수량을 최소 1개 이상 입력해주세요")
    private Integer quantity;

    public static OrderItems createEntity(OrderItemReqDto dto, Products p, Orders o) {
        return OrderItems.builder()
                .orders(o)
                .products(p)
                .category(p.getCategory())
                .price(p.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }


}
