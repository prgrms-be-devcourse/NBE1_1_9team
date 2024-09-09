package gc.cafe.domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "order_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId",nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId",nullable = false)
    private Product product;

    @Builder
    public OrderItem(Category category,
                     Long price,
                     int quantity) {
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public void assignOrder(Order order) {
        this.order = order;
    }

    public void assignProduct(Product product){
        this.product = product;
    }

}
