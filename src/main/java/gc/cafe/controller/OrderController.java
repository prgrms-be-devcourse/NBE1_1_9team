package gc.cafe.controller;


import gc.cafe.controller.dto.req.OrderReqDto;
import gc.cafe.controller.dto.res.OrderResDto;
import gc.cafe.model.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<String> addOrders(@Valid @RequestBody OrderReqDto dto) {
        return ResponseEntity.ok().body(orderService.addOrders(dto));
    }

    @GetMapping("")
    public ResponseEntity<List<OrderResDto>> getMyOrder(@RequestParam("email") String email) {
        return ResponseEntity.ok().body(orderService.getMyOrder(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable String id) {
        return ResponseEntity.ok().body(orderService.cancelOrder(id));
    }
}
