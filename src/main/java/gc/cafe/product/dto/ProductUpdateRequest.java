package gc.cafe.product.dto;

public record ProductUpdateRequest(
        String name,
        String category,
        int price,
        String description
) {
}
