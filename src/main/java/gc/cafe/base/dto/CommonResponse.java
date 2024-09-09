package gc.cafe.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse<T>(T result) {

    public static final CommonResponse<EmptyDto> EMPTY = new CommonResponse<>(new EmptyDto());
}
