package gc.cafe.controller.dto.common.entitydto.responsedto;

import gc.cafe.controller.dto.DTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductsResponseDTO extends DTO {
    private String productId;
    private String productName;
    private String category;
    private Long price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
