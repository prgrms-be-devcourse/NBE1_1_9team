package gc.cafe.domain.dto.response;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Category category;
    private Long price;
    private String description;


    public ProductResponseDto(Product entity) {
        this.productId = entity.getProductId();
        this.productName = entity.getProductName();
        this.category = entity.getCategory();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
    }
}
