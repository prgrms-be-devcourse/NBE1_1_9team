package gc.cafe.order.domain;

import gc.cafe.base.entity.BaseEntity;
import gc.cafe.order.dto.OrderUpdateRequest;
import gc.cafe.orderitem.domain.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String email;
    private String address;
    private String postcode;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private final List<OrderItem> orderItems = new ArrayList<>();

    public Order(String email, String address, String postcode) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = OrderStatus.ORDERED;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void update(OrderUpdateRequest request) {
        this.email = request.email();
        this.address = request.address();
        this.postcode = request.postcode();
    }
}
