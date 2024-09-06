package gc.cafe.model.dto;

import gc.cafe.model.entity.Products;
import gc.cafe.util.uuid.UuidUtils;
import lombok.*;

@Getter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {

    private String id;
    private String name;
    private String category;
    private Long price;
    private String description;
    private String createdAt;
    private String updatedAt;


    public static Products createWithNewId(ProductDto dto) {
        return Products.builder()
                .id(UuidUtils.generateId())
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }

    public static Products createWithExistingId(ProductDto dto) {
        return Products.builder()
                .id(UuidUtils.convertStringToBytes(dto.getId()))
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }
}
