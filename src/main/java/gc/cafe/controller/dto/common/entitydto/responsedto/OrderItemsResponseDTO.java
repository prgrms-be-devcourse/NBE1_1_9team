package gc.cafe.controller.dto.common.entitydto.responsedto;

import gc.cafe.controller.dto.DTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderItemsResponseDTO extends DTO {
    private Long seq;
    private ProductsResponseDTO productsResponseDTO;
    private String category;
    private Long price;
    private int quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderItemsResponseDTO(ProductsResponseDTO productsResponseDTO) {
        this.productsResponseDTO = productsResponseDTO;
    }
}
