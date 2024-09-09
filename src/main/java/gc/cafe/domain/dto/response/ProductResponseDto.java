package gc.cafe.domain.dto.response;

import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Category category;
    private Long price;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public ProductResponseDto(Product entity) {
        this.productId = entity.getProductId();
        this.productName = entity.getProductName();
        this.category = entity.getCategory();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
