package gc.cafe.order.controller;

import gc.cafe.base.dto.CommonResponse;
import gc.cafe.base.dto.EmptyDto;
import gc.cafe.order.dto.EmailVO;
import gc.cafe.order.dto.OrderDetailResponse;
import gc.cafe.order.dto.OrderListResponse;
import gc.cafe.order.dto.OrderRequest;
import gc.cafe.order.dto.OrderResponse;
import gc.cafe.order.dto.OrderUpdateRequest;
import gc.cafe.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderService orderService;

    @PostMapping
    public CommonResponse<OrderResponse> makeOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        return new CommonResponse<>(orderService.makeOrder(orderRequest));
    }

    @GetMapping
    public CommonResponse<OrderListResponse> getOrders(
            @RequestBody EmailVO emailVO
    ) {
        return new CommonResponse<>(orderService.getOrders(emailVO));
    }

    @GetMapping("/{orderId}")
    public CommonResponse<OrderDetailResponse> getOrderDetail(
            @PathVariable("orderId") Long orderId
    ) {
        return new CommonResponse<>(orderService.getOrderDetail(orderId));
    }

    @PutMapping("/{orderId}")
    public CommonResponse<OrderResponse> updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderUpdateRequest orderUpdateRequest
    ) {
        return new CommonResponse<>(orderService.updateOrder(orderId, orderUpdateRequest));
    }

    @DeleteMapping("/{orderId}")
    public CommonResponse<EmptyDto> deleteOrder(
            @PathVariable("orderId") Long orderId
    ) {
        orderService.deleteOrder(orderId);
        return CommonResponse.EMPTY;
    }

}
