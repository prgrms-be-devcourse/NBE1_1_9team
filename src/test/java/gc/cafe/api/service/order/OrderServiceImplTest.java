package gc.cafe.api.service.order;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.api.controller.order.request.OrderProductQuantity;
import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.api.service.order.response.OrderDetailResponse;
import gc.cafe.api.service.order.response.OrderResponse;
import gc.cafe.domain.order.Order;
import gc.cafe.domain.order.OrderRepository;
import gc.cafe.domain.order.OrderStatus;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static gc.cafe.domain.order.OrderStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class OrderServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @DisplayName("상품을 주문한다.")
    @Test
    void createOrder() {
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

        productRepository.saveAll(List.of(product1, product2));

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .email("test@gmail.com")
            .address("서울시 강남구")
            .postcode("125454")
            .orderProductQuantity(
                List.of(
                    OrderProductQuantity.builder()
                        .productId(1L)
                        .quantity(1)
                        .build(),
                    OrderProductQuantity.builder()
                        .productId(2L)
                        .quantity(2)
                        .build()
                )
            )
            .build();

        //when
        OrderResponse response = orderService.createOrder(request);

        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).hasSize(1)
            .extracting("id", "email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                tuple(orders.get(0).getId(), "test@gmail.com", "서울시 강남구", "125454", ORDERED)
            );

        assertThat(response)
            .extracting("id", "email", "address", "postcode", "orderStatus")
            .containsExactlyInAnyOrder(
                response.getId(), "test@gmail.com", "서울시 강남구", "125454", ORDERED
            );

        assertThat(response.getOrderDetails())
            .hasSize(2)
            .extracting("category", "price","quantity")
            .containsExactlyInAnyOrder(
                tuple("원두", 50000L, 1),
                tuple("음료", 3000L, 2)
            );
    }

}