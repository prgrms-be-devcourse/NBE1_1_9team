package gc.cafe.domain.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(nullable = false, length = 20)
    private String productName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false, length = 500)
    private String description;
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Product(String productName,
                   Category category,
                   Long price,
                   String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.assignProduct(this);
    }
}
