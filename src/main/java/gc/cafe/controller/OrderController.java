package gc.cafe.controller;

import gc.cafe.domain.dto.request.OrderRequestDto;
import gc.cafe.domain.entity.Order;
import gc.cafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<Order> order(@RequestBody OrderRequestDto requestDto) {
        Order order = orderService.createOrder(requestDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
