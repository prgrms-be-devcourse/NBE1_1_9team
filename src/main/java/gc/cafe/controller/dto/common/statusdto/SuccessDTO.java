package gc.cafe.controller.dto.common.statusdto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessDTO extends ResponseStatusDTO {
    private String successType;
}
