package gc.cafe.api.controller.order.request;

import gc.cafe.api.service.order.request.OrderCreateServiceRequest;
import gc.cafe.domain.order.OrderStatus;
import gc.cafe.domain.orderproduct.OrderProduct;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRequest {

    private String email;

    private String address;

    private String postcode;

    private List<Long> productsIds;

    @Builder
    private OrderCreateRequest(String email, String address, String postcode, List<Long> productsIds) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.productsIds = productsIds;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
            .email(email)
            .address(address)
            .postcode(postcode)
            .productsIds(productsIds)
            .build();
    }
}
