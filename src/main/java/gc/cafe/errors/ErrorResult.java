package gc.cafe.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResult {

    private int status;
    private Object errors;
    private LocalDateTime occurrenceTime;

    public ErrorResult(int status, Object errors) {
        this.status = status;
        this.errors = errors;
        this.occurrenceTime = LocalDateTime.now();
    }
}
