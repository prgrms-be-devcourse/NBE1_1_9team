package gc.cafe.domain.dto.request;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    private String productName;
    private Category category;
    private Long price;
    private String description;

    public Product toEntity() {
        return Product.builder()
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .build();
    }
}
