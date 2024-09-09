package gc.cafe.entity;

import gc.cafe.controller.dto.common.entitydto.responsedto.OrderItemsResponseDTO;
import gc.cafe.controller.dto.common.entitydto.responsedto.OrdersResponseDTO;
import gc.cafe.util.OrderStatus;
import gc.cafe.util.UUIDConversion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {
    @Id @Column(length = 16)
    private byte[] orderId;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false, length = 200)
    private String address;
    @Column(nullable = false, length = 200)
    private String postcode;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems = new ArrayList<>();

    //** internal logic **//
    public byte[] getOrderId() {
        return orderId;
    }

    public String getEmail() {
        return email;
    }
    @Builder
    private Orders(String email, String address, String postcode) {
        orderId = UUIDConversion.uuidToBytes(UUID.randomUUID());
        orderStatus = OrderStatus.UNPROCESSED;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
    }

    public void addOrderItem(OrderItems orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public Orders setTo(Orders newOrder) {
        this.address = newOrder.address;
        this.postcode = newOrder.postcode;
        this.orderStatus = newOrder.orderStatus;
        this.orderItems = newOrder.orderItems;
        return this;
    }

    public static OrdersResponseDTO getResponseDTOFromOrders(Orders orders) {
        OrdersResponseDTO ordersResponseDTO = new OrdersResponseDTO();
        ordersResponseDTO.setOrderId(UUIDConversion.bytesToUUID(orders.orderId).toString());
        ordersResponseDTO.setEmail(orders.email);
        ordersResponseDTO.setAddress(orders.address);
        ordersResponseDTO.setPostcode(orders.postcode);
        ordersResponseDTO.setOrderStatus(orders.orderStatus);
        ordersResponseDTO.setCreatedAt(orders.createdAt);
        ordersResponseDTO.setUpdatedAt(orders.updatedAt);
        for (OrderItems orderItem : orders.orderItems) {
            OrderItemsResponseDTO orderItemsResponseDTO = OrderItems.getResponseDTOFromOrderItems(orderItem);
            ordersResponseDTO.addOrderItemsResponseDTO(orderItemsResponseDTO);
        }
        return ordersResponseDTO;
    }

    //** business logic **//
    public void process() {
        for (OrderItems orderItem : orderItems) {
            //상품발송 및 주문처리 내용의 코드
            orderItem.reduceProductQuantity();
        }
        this.orderStatus = OrderStatus.PROCESSED;
    }

    //** test logic **//

    public String getEmailforTest() {
        return email;
    }

    public String getAddressforTest() {
        return address;
    }

    public String getPostcodeforTest() {
        return postcode;
    }

    public OrderStatus getOrderStatusforTest() {
        return orderStatus;
    }

    public LocalDateTime getCreatedAtforTest() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAtforTest() {
        return updatedAt;
    }

    public List<OrderItems> getOrderItemsforTest() {
        return orderItems;
    }

}
