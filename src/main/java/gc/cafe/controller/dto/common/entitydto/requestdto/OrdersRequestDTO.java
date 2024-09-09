package gc.cafe.controller.dto.common.entitydto.requestdto;

import gc.cafe.controller.dto.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrdersRequestDTO extends DTO {
    @NotBlank @Email
    private String email;
    @NotBlank @Size(max = 200)
    private String address;
    @NotBlank @Size(max = 200)
    private String postcode;
}
