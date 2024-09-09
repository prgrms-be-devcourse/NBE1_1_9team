package gc.cafe.controller.dto.req;

import gc.cafe.model.entity.Products;
import gc.cafe.util.uuid.UuidUtils;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReqDto {

    private String id;

    @NotNull(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private String category;

    @NotNull(message = "가격은 필수 입력값입니다.")
    @Min(value = 1000, message = "최소 가격은 1000원입니다.")
    private Long price;

    @NotNull(message = "설명은 필수 입력값입니다.")
    private String description;

    private String createdAt;
    private String updatedAt;

    public static Products createWithNewId(ProductReqDto dto) {
        return Products.builder()
                .id(UuidUtils.generateEntityId())
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .build();
    }

    public static Products createWithExistingId(ProductReqDto dto) {
        return Products.builder()
                .id(UuidUtils.convertStringToBytes(dto.getId()))
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
