package gc.cafe.controller.dto.res;

import gc.cafe.model.entity.OrderStatus;
import gc.cafe.util.uuid.UuidUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderResDto {

    private String id;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus status;
    private String orderDate;
    private List<OrderItemResDto> orderItemDtos;

    public OrderResDto(byte[] id, String email, String address, String postcode, OrderStatus status, LocalDateTime orderDate, List<OrderItemResDto> orderItemDtos) {
        this.id = UuidUtils.convertBytesToString(id);
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.status = status;
        this.orderDate = orderDate.toString();
        this.orderItemDtos = orderItemDtos;
    }
}
