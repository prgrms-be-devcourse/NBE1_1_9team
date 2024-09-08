package gc.cafe.domain.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "order_items")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {
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

    public void assignOrder(Order order) {
        this.order = order;
    }

    public void assignProduct(Product product){
        this.product = product;
    }
}
