package gc.cafe.controller.dto.res;

import lombok.*;


@Getter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResDto {

    private String id;
    private String name;
    private String category;
    private Long price;
    private String description;
    private String createdAt;
    private String updatedAt;


}
