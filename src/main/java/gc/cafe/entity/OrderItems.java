package gc.cafe.entity;

import gc.cafe.controller.dto.common.entitydto.requestdto.OrderItemsRequestDTO;
import gc.cafe.controller.dto.common.entitydto.responsedto.OrderItemsResponseDTO;
import gc.cafe.controller.dto.common.entitydto.responsedto.ProductsResponseDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_order_id", columnList = "order_id"))
public class OrderItems {
    @Id
    @GeneratedValue
    private Long seq;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;
    @Column(nullable = false, length = 50)
    private String category;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private int quantity;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //** internal logic **//

    @Builder
    private OrderItems(Products product, String category, Long price, int quantity) {
        this.product = product;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    public void setOrder(Orders order) {
        this.order = order;
    }
    public static OrderItems getOrderItemsFromRequestDTO(OrderItemsRequestDTO dto) {
        return OrderItems.builder()
                .product(Products.getProductsFromRequestDTO(dto.getProductsRequestDTO()))
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }

    public static OrderItemsResponseDTO getResponseDTOFromOrderItems(OrderItems orderItems) {
        ProductsResponseDTO productsResponseDTO = Products.getResponseDTOFromProducts(orderItems.product);
        OrderItemsResponseDTO orderItemsResponseDTO = new OrderItemsResponseDTO(productsResponseDTO);
        orderItemsResponseDTO.setSeq(orderItems.seq);
        orderItemsResponseDTO.setCategory(orderItems.category);
        orderItemsResponseDTO.setPrice(orderItems.price);
        orderItemsResponseDTO.setQuantity(orderItems.quantity);
        orderItemsResponseDTO.setCreatedAt(orderItems.createdAt);
        orderItemsResponseDTO.setUpdatedAt(orderItems.updatedAt);
        return orderItemsResponseDTO;
    }

    //** business logic **//
    public void reduceProductQuantity() {
        product.reduceQuantity(quantity);
    }
}
