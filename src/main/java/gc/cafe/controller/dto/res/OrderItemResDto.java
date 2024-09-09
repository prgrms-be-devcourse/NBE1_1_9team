package gc.cafe.controller.dto.res;


import lombok.*;

@Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResDto {

    private Long seq;
    private ProductListDto product;
    private Integer quantity;
}
