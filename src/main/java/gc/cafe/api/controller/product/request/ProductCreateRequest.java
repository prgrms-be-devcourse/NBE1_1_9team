package gc.cafe.api.controller.product.request;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    private String name;
    private String category;
    private Long price;
    private String description;

    @Builder
    private ProductCreateRequest(String name, String category, Long price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
            .name(name)
            .category(category)
            .price(price)
            .description(description)
            .build();
    }
}
