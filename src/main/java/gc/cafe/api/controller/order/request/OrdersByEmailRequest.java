package gc.cafe.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersByEmailRequest {

    private String email;

    @Builder
    private OrdersByEmailRequest(String email) {
        this.email = email;
    }
}
