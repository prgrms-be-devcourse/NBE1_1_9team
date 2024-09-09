package gc.cafe.controller.dto.common.statusdto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO extends ResponseStatusDTO {
    private String errorType;
    private String errorMessage;
}
