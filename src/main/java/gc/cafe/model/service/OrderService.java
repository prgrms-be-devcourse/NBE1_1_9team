package gc.cafe.model.service;

import gc.cafe.controller.dto.req.OrderReqDto;
import gc.cafe.controller.dto.res.OrderResDto;

import java.util.List;

public interface OrderService {
    String addOrders(OrderReqDto dto);

    void approveOrders();

    List<OrderResDto> getMyOrder(String email);

    String cancelOrder(String id);
}
