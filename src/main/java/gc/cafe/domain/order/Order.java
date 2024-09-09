package gc.cafe.domain.order;

import gc.cafe.domain.BaseEntity;
import gc.cafe.domain.orderproduct.OrderProduct;
import gc.cafe.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder
    private Order(String email, String address, String postcode, List<Product> products, Map<Long,Integer> orderProducts) {
        this.email = email;
        this.address = Address.builder()
            .address(address)
            .postcode(postcode)
            .build();
        this.orderStatus = OrderStatus.ORDERED;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product, orderProducts.get(product.getId())))
            .collect(Collectors.toList());
    }

    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
