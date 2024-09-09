package gc.cafe.controller.dto.common.entitydto.requestdto;

import gc.cafe.controller.dto.DTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderItemsRequestDTO extends DTO {
    @Valid
    private ProductsRequestDTO productsRequestDTO;

    @NotBlank @Size(max = 50)
    private String category;
    @NotBlank
    private Long price;
    @NotBlank
    private int quantity;
}
