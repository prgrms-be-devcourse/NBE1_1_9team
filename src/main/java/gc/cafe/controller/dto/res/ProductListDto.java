package gc.cafe.controller.dto.res;


import gc.cafe.util.uuid.UuidUtils;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductListDto {
    private String id;
    private String name;
    private String category;
    private Long price;

    public ProductListDto(byte[] id, String name, String category, Long price) {
        this.id = UuidUtils.convertBytesToString(id);
        this.name = name;
        this.category = category;
        this.price = price;
    }
}
