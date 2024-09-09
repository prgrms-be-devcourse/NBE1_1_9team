package gc.cafe.controller.dto.common.entitydto.requestdto;

import gc.cafe.controller.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductsRequestDTO extends DTO {
    @NotBlank
    private String productId;
    @NotBlank @Size(max = 20)
    private String productName;
    @NotBlank @Size(max = 50)
    private String category;
    @NotBlank
    private Long price;
    @Size(max = 500)
    private String description;
}
