package gc.cafe.order.dto;

public record OrderProductResponse(
        String productName,
        int quantity
) {
}
