package gc.cafe.service;

import gc.cafe.order.domain.Order;
import gc.cafe.order.dto.*;
import gc.cafe.order.repository.OrderRepository;
import gc.cafe.order.service.OrderService;
import gc.cafe.orderitem.repository.OrderItemRepository;
import gc.cafe.product.domain.Product;
import gc.cafe.product.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private Product savedProduct;
    private Order savedOrder;

    @BeforeEach
    void setUp() {
        savedProduct = productRepository.save(new Product("Coffee", "Beverage", 5000, "Delicious coffee"));

        OrderRequest request = new OrderRequest("test@example.com", "Seoul", "12345", List.of(
                new OrderProductResponse("Coffee", 2)
        ));
        OrderResponse orderResponse = orderService.makeOrder(request);
        savedOrder = orderRepository.findById(orderResponse.orderId()).orElseThrow();
    }

    @AfterEach
    void tearDown() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 생성 테스트")
    void makeOrderTest() {
        // given
        OrderRequest request = new OrderRequest("user@example.com", "Busan", "67890", List.of(
                new OrderProductResponse("Coffee", 3)
        ));

        // when
        OrderResponse response = orderService.makeOrder(request);

        // then
        assertThat(orderRepository.findById(response.orderId()))
                .isPresent()
                .get()
                .extracting(Order::getEmail)
                .isEqualTo("user@example.com");

        assertThat(orderRepository.findById(response.orderId()))
                .isPresent()
                .get()
                .extracting(order -> order.getOrderItems().size())
                .isEqualTo(1);
    }

    @Test
    @DisplayName("주문 목록 조회 테스트")
    void getOrdersTest() {
        // when
        OrderListResponse response = orderService.getOrders(new EmailVO("test@example.com"));

        // then
        assertThat(response.orders())
                .isNotEmpty()
                .extracting(OrderSimpleResponse::email)
                .containsExactly("test@example.com");

        assertThat(response.orders())
                .extracting(OrderSimpleResponse::totalPrice)
                .containsExactly(10000);
    }

    @Test
    @DisplayName("주문 상세 조회 테스트")
    void getOrderDetailTest() {
        // when
        OrderDetailResponse response = orderService.getOrderDetail(savedOrder.getId());

        // then
        assertThat(response)
                .extracting(OrderDetailResponse::email, OrderDetailResponse::address, OrderDetailResponse::postcode)
                .containsExactly("test@example.com", "Seoul", "12345");

        assertThat(response.products())
                .hasSize(1)
                .extracting(OrderProductResponse::productName, OrderProductResponse::quantity)
                .containsExactly(tuple("Coffee", 2));
    }

    @Test
    @DisplayName("주문 수정 테스트")
    void updateOrderTest() {
        // given
        OrderUpdateRequest request = new OrderUpdateRequest("update@example.com", "Incheon", "98765", List.of(
                new OrderProductResponse("Coffee", 5)
        ));

        // when
        OrderResponse response = orderService.updateOrder(savedOrder.getId(), request);

        // then
        assertThat(orderRepository.findById(response.orderId()))
                .isPresent()
                .get()
                .extracting(Order::getEmail)
                .isEqualTo("update@example.com");

        assertThat(orderRepository.findById(response.orderId()))
                .isPresent()
                .get()
                .extracting(order -> order.getOrderItems().get(0).getQuantity())
                .isEqualTo(5);
    }

    @Test
    @DisplayName("주문 삭제 테스트")
    void deleteOrderTest() {
        // when
        orderService.deleteOrder(savedOrder.getId());

        // then
        assertThat(orderRepository.findById(savedOrder.getId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 주문 조회 시 예외 발생 테스트")
    void getOrderNotFoundTest() {
        // when & then
        Long invalidOrderId = -1L;

        assertThatThrownBy(() -> orderService.getOrderDetail(invalidOrderId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 주문은 없습니다.");
    }
}
