package gc.cafe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "Product is not found"),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
