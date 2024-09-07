package gc.cafe.api.controller.product.request;

import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ProductUpdateRequest {

    private String name;
    private String category;
    private Long price;
    private String description;

    @Builder
    private ProductUpdateRequest(String name, String category, Long price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public ProductUpdateServiceRequest toServiceRequest() {
        return ProductUpdateServiceRequest.builder()
            .name(name)
            .category(category)
            .price(price)
            .description(description)
            .build();
    }
}
