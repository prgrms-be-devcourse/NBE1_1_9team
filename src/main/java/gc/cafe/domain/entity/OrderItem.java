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
    @Column(nullable = false, length = 50)
    private String category;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private Long orderId;
    @Column(nullable = false)
    private Long productId;

}
