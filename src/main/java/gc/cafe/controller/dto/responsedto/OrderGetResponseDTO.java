package gc.cafe.controller.dto.responsedto;

import gc.cafe.controller.dto.DTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.ProductsRequestDTO;
import lombok.Data;

import java.util.List;
@Data
public class OrderGetResponseDTO extends DTO {
    List<ProductsRequestDTO> productsRequestDTOS;

    public OrderGetResponseDTO(List<ProductsRequestDTO> productsRequestDTOS) {
        this.productsRequestDTOS = productsRequestDTOS;
    }
}
