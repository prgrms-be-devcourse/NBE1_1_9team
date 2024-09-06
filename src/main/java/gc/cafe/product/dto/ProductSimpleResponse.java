package gc.cafe.product.dto;

public record ProductSimpleResponse(
        String name,
        String category,
        int price
) {
}
