package gc.cafe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 200)
    private String address;
    @Column(nullable = false, length = 200)
    private String postcode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OrderStatus orderStatus;

}
