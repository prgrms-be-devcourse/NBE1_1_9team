package gc.cafe.product.entity;

import gc.cafe.base.entity.BaseEntity;
import gc.cafe.product.dto.ProductUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String productName;
    private String category;
    private int price;
    private String description;

    public Product(String productName, String category, int price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public void update(ProductUpdateRequest request) {
        this.productName = request.name();
        this.category = request.category();
        this.price = request.price();
        this.description = request.description();
    }
}
