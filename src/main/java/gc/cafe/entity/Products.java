package gc.cafe.entity;

import gc.cafe.controller.dto.common.entitydto.requestdto.ProductsRequestDTO;
import gc.cafe.controller.dto.common.entitydto.responsedto.ProductsResponseDTO;
import gc.cafe.util.UUIDConversion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Products {
    @Id
    @Column(length = 16)
    private byte[] productId;
    @Column(nullable = false, length = 20)
    private String productName;
    @Column(nullable = false, length = 50)
    private String category;
    @Column(nullable = false)
    private Long price;
    @Column(length = 500)
    private String description;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItems> orderItems;

    //** internal logic**//
    @Builder
    private Products(String productName, String category, Long price, String description) {
        this.productId = UUIDConversion.uuidToBytes(UUID.randomUUID());
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public byte[] getProductId() {
        return productId;

    }
    public Products setTo(Products newProduct) {
        this.productName = newProduct.productName;
        this.category = newProduct.category;
        this.price = newProduct.price;
        this.description = newProduct.description;
        return this;
    }
    public static ProductsRequestDTO getRequestDTOFromProducts(Products products) {
        ProductsRequestDTO dto = new ProductsRequestDTO();
        dto.setProductId(UUIDConversion.bytesToUUID(products.productId).toString());
        dto.setProductName(products.productName);
        dto.setCategory(products.category);
        dto.setPrice(products.price);
        dto.setDescription(products.description);
        return dto;
    }
    public static Products getProductsFromRequestDTO(ProductsRequestDTO dto) {
        return Products.builder()
                .productName(dto.getProductName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
    public static ProductsResponseDTO getResponseDTOFromProducts(Products product) {
        ProductsResponseDTO productsResponseDTO = new ProductsResponseDTO();
        productsResponseDTO.setProductId(UUIDConversion.bytesToUUID(product.getProductId()).toString());
        productsResponseDTO.setProductName(product.productName);
        productsResponseDTO.setCategory(product.category);
        productsResponseDTO.setPrice(product.price);
        productsResponseDTO.setDescription(product.description);
        productsResponseDTO.setCreatedAt(product.createdAt);
        productsResponseDTO.setUpdatedAt(product.updatedAt);
        return productsResponseDTO;
    }

    //** business logic **//
    public void reduceQuantity(int quantityToReduce) {
        //여긴 왜 quantity가 없냐
    }

    //** test logic**//

    public String getProductNameforTest() {
        return productName;
    }

    public String getCategoryforTest() {
        return category;
    }

    public Long getPriceforTest() {
        return price;
    }

    public String getDescriptionforTest() {
        return description;
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
