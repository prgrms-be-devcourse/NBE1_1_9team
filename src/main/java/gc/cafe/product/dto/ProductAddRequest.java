package gc.cafe.product.dto;

public record ProductAddRequest(
        String name,
        String category,
        int price,
        String description
) {
}
