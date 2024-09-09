package gc.cafe.controller.dto.responsedto;
import gc.cafe.controller.dto.DTO;

import gc.cafe.controller.dto.common.entitydto.responsedto.OrdersResponseDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class FindOrdersByEmailResponseDTO extends DTO{
    List<OrdersResponseDTO> ordersResponseDTOList = new ArrayList<>();
    public void addOrderResponseDTO(OrdersResponseDTO ordersResponseDTO) {
        ordersResponseDTOList.add(ordersResponseDTO);
    }
}
