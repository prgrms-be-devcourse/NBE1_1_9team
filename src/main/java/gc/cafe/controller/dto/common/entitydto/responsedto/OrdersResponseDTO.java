package gc.cafe.controller.dto.common.entitydto.responsedto;

import gc.cafe.controller.dto.DTO;
import gc.cafe.util.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrdersResponseDTO extends DTO {
    private String orderId;
    private String email;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItemsResponseDTO> orderItemsResponseDTOs = new ArrayList<>();

    public void addOrderItemsResponseDTO(OrderItemsResponseDTO orderItemsResponseDTO) {
        orderItemsResponseDTOs.add(orderItemsResponseDTO);
    }

}
