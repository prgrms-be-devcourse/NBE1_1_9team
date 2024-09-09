package gc.cafe.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    /**
     * db 데이터의 로직
     * @param e
     * @return
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResult> illegalExceptionHandler(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    /**
     * @Valid 검증 오류 처리
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorResult(HttpStatus.BAD_REQUEST.value(), errors));
    }
}
