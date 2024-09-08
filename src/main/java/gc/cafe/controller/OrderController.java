package gc.cafe.controller;

import gc.cafe.domain.dto.Response;
import gc.cafe.domain.dto.request.OrderRequestDto;
import gc.cafe.domain.dto.response.OrderResponseDto;
import gc.cafe.domain.entity.Order;
import gc.cafe.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<Long>> createOrder(@RequestBody OrderRequestDto requestDto) {
        Long orderId = orderService.createOrder(requestDto);
        return ResponseEntity.ok().body(Response.success(orderId));
    }

    @GetMapping("read")
    public ResponseEntity<Response<OrderResponseDto>> readOrder(@RequestParam Long orderId){
        Order order = orderService.readOrder(orderId);
        return ResponseEntity.ok().body(Response.success(new OrderResponseDto(order)));
    }

    @GetMapping("find")
    public ResponseEntity<Response<List<OrderResponseDto>>> readByEmail(@RequestParam String email){
        List<OrderResponseDto> responseDtos = orderService.readByEmail(email)
                .stream()
                .map(OrderResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(Response.success(responseDtos));
    }


}
