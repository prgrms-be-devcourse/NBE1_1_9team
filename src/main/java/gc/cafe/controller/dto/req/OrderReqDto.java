package gc.cafe.controller.dto.req;

import gc.cafe.model.entity.OrderStatus;
import gc.cafe.model.entity.Orders;
import gc.cafe.util.uuid.UuidUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter @Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderReqDto {

    private String id;

    @NotNull(message = "이메일을 입력하세요.")
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$", message = "이메일 형식이 잘못되었습니다.")
    private String email;

    @NotNull(message = "주소를 입력하세요")
    private String address;

    @NotNull(message = "우편번호를 입력하세요")
    private String postcode;
    private OrderStatus status;
    private String updatedAt;

    @NotNull(message = "최소 1개의 주문 항목이 있어야 합니다.")
    @Size(min = 1, message = "최소 1개의 주문 항목이 있어야 합니다.")
    private List<OrderItemReqDto> orderItems;

    public static Orders createWithNewId(OrderReqDto dto) {
        return Orders.builder()
                .id(UuidUtils.generateEntityId())
                .email(dto.getEmail())
                .address(dto.getAddress())
                .postcode(dto.getPostcode())
                .status(OrderStatus.REQUESTED)
                .build();
    }
}
