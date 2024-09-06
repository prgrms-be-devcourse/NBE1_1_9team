package gc.cafe.product.dto;

public record ProductDetailResponse (
        String name,
        String category,
        int price,
        String description
) {
}
