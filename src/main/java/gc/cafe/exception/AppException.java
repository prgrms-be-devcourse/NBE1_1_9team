package gc.cafe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
  private ErrorCode errorCode;

  @Override
  public String toString() {
      return errorCode.getMessage();
  }
}
