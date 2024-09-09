package gc.cafe.order.dto;

import java.util.List;

public record OrderListResponse (
        List<OrderSimpleResponse> orders
) {
}
