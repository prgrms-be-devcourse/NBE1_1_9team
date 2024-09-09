package gc.cafe.controller;

import gc.cafe.controller.dto.DTO;
import gc.cafe.controller.dto.common.entitydto.responsedto.OrdersResponseDTO;
import gc.cafe.controller.dto.common.statusdto.ResponseStatusDTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.ProductsRequestDTO;
import gc.cafe.controller.dto.common.statusdto.SuccessDTO;
import gc.cafe.controller.dto.requestdto.OrderPostRequestDTO;
import gc.cafe.controller.dto.common.statusdto.ErrorDTO;
import gc.cafe.controller.dto.responsedto.FindOrdersByEmailResponseDTO;
import gc.cafe.controller.dto.responsedto.OrderGetResponseDTO;
import gc.cafe.entity.Orders;
import gc.cafe.entity.Products;
import gc.cafe.service.OrderService;
import gc.cafe.service.ProductService;
import gc.cafe.util.UUIDConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrdersController {
    private final OrderService orderService;
    private final ProductService productService;

    //주문페이지 _현재 상품목록 및 각 정보(재고등등) 보여주기
    @GetMapping
    public OrderGetResponseDTO orderPage() {
        //현재 상품들 리스트
        List<Products> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new OrderGetResponseDTO(new ArrayList<>());
        }

        List<ProductsRequestDTO> productsRequestDTOs = products.stream()
                .map(Products::getRequestDTOFromProducts)
                .collect(Collectors.toList());
        return new OrderGetResponseDTO(productsRequestDTOs);
    }

    //주문하기 _dto에서 정보를 꺼내서, order를 만들고, orderitems를 만들고, list<orderitems>로 order로 생성
    @PostMapping
    public ResponseStatusDTO order(@Validated OrderPostRequestDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorDTO("404", "illegal form data");
        }
        Orders newOrder = orderService.generateOrderByDTO(dto);
        orderService.registerOrder(newOrder);

        return new SuccessDTO("200");
    }

    //이메일로 주문조회하기
    @GetMapping("/{email}")
    public DTO findOrderByEmail(@PathVariable("email") String email) {
        List<Orders> orders = orderService.findOrdersByEmail(email);
        if (orders.isEmpty()) {
            return new FindOrdersByEmailResponseDTO();
        }
        return generateOrdersResponseDTO(orders);
    }
    private FindOrdersByEmailResponseDTO generateOrdersResponseDTO(List<Orders> orders) {
        FindOrdersByEmailResponseDTO findOrdersByEmailResponseDTO = new FindOrdersByEmailResponseDTO();

        for (Orders order : orders) {
            OrdersResponseDTO ordersResponseDTO = Orders.getResponseDTOFromOrders(order);
            findOrdersByEmailResponseDTO.addOrderResponseDTO(ordersResponseDTO);
        }
        return findOrdersByEmailResponseDTO;
    }

    //주문 단건 조회
    @GetMapping("/{orderId}")
    public DTO findOneOrder(@PathVariable("orderId") String orderId) {
        Optional<Orders> order = orderService.findOrder(UUIDConversion.uuidToBytes(UUID.fromString(orderId)));
        if (order.isEmpty()) {
            return new ErrorDTO("404", "not found order");
        }
        return Orders.getResponseDTOFromOrders(order.get());
    }

    //주문수정하기
    @PutMapping("/{orderId}")
    public ResponseStatusDTO modifyOrder(@PathVariable("orderId") String orderId,
                                         @Validated OrderPostRequestDTO dto, BindingResult result) {
        byte[] orderIdToModify = UUIDConversion.uuidToBytes(UUID.fromString(orderId));

        if (result.hasErrors()) {
            return new ErrorDTO("404", "illegal form data");
        }
        if (orderService.findOrder(orderIdToModify).isEmpty()) {
            return new ErrorDTO("404", "not found order");
        }
        Orders newOrder = orderService.generateOrderByDTO(dto);
        orderService.updateOrder(orderIdToModify, newOrder);

        return new SuccessDTO("200");
    }
    //주문취소
    @DeleteMapping("/{orderId}")
    public ResponseStatusDTO cancelOrder(@PathVariable("orderId") String orderId) {
        byte[] orderIdToCancel = UUIDConversion.uuidToBytes(UUID.fromString(orderId));

        if (orderService.findOrder(orderIdToCancel).isEmpty()) {
            return new ErrorDTO("404", "not found order");
        }
        orderService.cancelOrder(orderIdToCancel);
        return new SuccessDTO("200");
    }
}
