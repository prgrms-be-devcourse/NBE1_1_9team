package gc.cafe.api.service.order.request;

import lombok.Builder;

import java.util.List;

public class OrderCreateServiceRequest {

    private String email;
    private String address;
    private String postcode;
    private List<Long> productsIds;


    @Builder
    private OrderCreateServiceRequest(String email, String address, String postcode, List<Long> productsIds) {
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.productsIds = productsIds;
    }
}
