package gc.cafe.order.dto;

public record OrderSimpleResponse(
        Long orderId,
        String email,
        String address,
        String postCode,
        int totalPrice
) {
}
