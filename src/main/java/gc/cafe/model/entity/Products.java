package gc.cafe.model.entity;


import gc.cafe.model.dto.ProductDto;
import gc.cafe.util.uuid.UuidUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Builder
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Products {

    @Id
    @Column(name = "product_id" , length = 16)
    private byte[] id;

    @Column(name = "product_name", nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Long price;

    @Column(length = 500)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "products")
    List<OrderItems> orderItemsList = new ArrayList<>();

    public static ProductDto toDto(Products p) {
        return ProductDto.builder()
                .id(UuidUtils.bytesToString(p.getId()))
                .name(p.getName())
                .category(p.getCategory())
                .price(p.getPrice())
                .description(p.getDescription())
                .createdAt(p.getCreatedAt().toString())
                .updatedAt(p.getUpdatedAt().toString())
                .build();
    }
}
