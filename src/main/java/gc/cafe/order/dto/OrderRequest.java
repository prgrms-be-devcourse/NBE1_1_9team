package gc.cafe.order.dto;

import java.util.List;

public record OrderRequest (
        String email,
        String address,
        String postcode,
        List<OrderProductResponse> products
) {
}
