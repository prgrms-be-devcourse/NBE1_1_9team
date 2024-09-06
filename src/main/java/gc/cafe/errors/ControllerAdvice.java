package gc.cafe.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ErrorResult> illegalExceptionHandler(IllegalArgumentException e) {
//        e.printStackTrace();
        return ResponseEntity.badRequest().body(new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
