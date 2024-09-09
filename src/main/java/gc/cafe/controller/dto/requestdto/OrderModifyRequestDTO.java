package gc.cafe.controller.dto.requestdto;

import gc.cafe.controller.dto.DTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.OrderItemsRequestDTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.OrdersRequestDTO;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class OrderModifyRequestDTO extends DTO {
    //Order정보와 OrderItems를 같이 보내면서 요청
    @Valid
    OrdersRequestDTO ordersRequestDTO;
    @Valid
    List<OrderItemsRequestDTO> orderItemsDTOs;
}
