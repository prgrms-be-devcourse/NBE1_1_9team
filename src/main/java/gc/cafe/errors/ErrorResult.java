package gc.cafe.errors;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResult {

    private int status;
    private String message;
    private LocalDateTime occurrenceTime;

    public ErrorResult(int status, String message) {
        this.status = status;
        this.message = message;
        this.occurrenceTime = LocalDateTime.now();
    }
}
