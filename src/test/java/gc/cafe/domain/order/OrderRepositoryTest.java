package gc.cafe.domain.order;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static gc.cafe.domain.order.OrderStatus.ORDERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Transactional
class OrderRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("이메일을 통해 주문 목록을 조회한다.")
    @Test
    void findOrdersByEmail() {
        //given
        String email = "test@gmail.com";

        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        Product product3 = Product.builder()
            .name("스타벅스 베이글")
            .category("베이커리")
            .price(5000L)
            .description("베이글")
            .build();

        productRepository.saveAll(List.of(product1, product2,product3));

        Order order1 = Order.builder()
            .email(email)
            .address("서울시 강남구")
            .postcode("125454")
            .orderProducts(Map.of(1L, 1, 2L, 2))
            .products(List.of(
                product1,
                product2
            ))
            .build();

        Order order2 = Order.builder()
            .email(email)
            .address("서울시 강남구")
            .postcode("125454")
            .orderProducts(Map.of(1L, 2, 3L, 4))
            .products(List.of(
                product1,
                product3
            ))
            .build();

        orderRepository.saveAll(List.of(order1, order2));

        //when
        List<Order> findOrdersByEmail = orderRepository.findByEmail(email);

        //then
        assertThat(findOrdersByEmail).hasSize(2)
            .extracting("email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED),
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(findOrdersByEmail.get(0).getOrderProducts()).hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );

        assertThat(findOrdersByEmail.get(1).getOrderProducts()).hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 2),
                tuple("베이커리", 5000L, 4)
            );

    }

    @DisplayName("주문 상태로 주문 목록을 조회한다.")
    @Test
    void findOrdersByOrderStatus() {
        //given
        Product product1 = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        Product product2 = Product.builder()
            .name("스타벅스 라떼")
            .category("음료")
            .price(3000L)
            .description("에스프레소")
            .build();

        Product product3 = Product.builder()
            .name("스타벅스 베이글")
            .category("베이커리")
            .price(5000L)
            .description("베이글")
            .build();

        productRepository.saveAll(List.of(product1, product2,product3));

        Order order1 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProducts(Map.of(1L, 1, 2L, 2))
            .products(List.of(
                product1,
                product2
            ))
            .build();

        Order order2 = Order.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProducts(Map.of(1L, 2, 3L, 4))
            .products(List.of(
                product1,
                product3
            ))
            .build();

        orderRepository.saveAll(List.of(order1, order2));
        //when
        List<Order> findOrdersByOrderStatus = orderRepository.findByOrderStatus(ORDERED);
        //then
        assertThat(findOrdersByOrderStatus).hasSize(2)
            .extracting("email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED),
                tuple("test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(findOrdersByOrderStatus.get(0).getOrderProducts()).hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );

        assertThat(findOrdersByOrderStatus.get(1).getOrderProducts()).hasSize(2)
            .extracting("category", "price", "quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 2),
                tuple("베이커리", 5000L, 4)
            );
    }
}