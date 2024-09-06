package gc.cafe.order.dto;

import java.util.List;

public record OrderUpdateRequest (
        String email,
        String address,
        String postcode,
        List<OrderProductResponse> products
) {
}
