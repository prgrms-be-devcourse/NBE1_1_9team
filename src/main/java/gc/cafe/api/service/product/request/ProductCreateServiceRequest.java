package gc.cafe.api.service.product.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateServiceRequest {

    private String name;
    private String category;
    private Long price;
    private String description;

    @Builder
    private ProductCreateServiceRequest(String name, String category, Long price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
