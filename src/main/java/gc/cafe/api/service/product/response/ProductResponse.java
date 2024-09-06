package gc.cafe.api.service.product.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private String category;
    private Long price;
    private String description;

    @Builder
    private ProductResponse(Long id, String name, String category, Long price, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
