package gc.cafe.product.dto;

import java.util.List;

public record ProductListResponse(
        List<ProductSimpleResponse> result
) {
}
