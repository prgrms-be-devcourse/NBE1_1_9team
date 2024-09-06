package gc.cafe.controller;

import gc.cafe.model.dto.ProductDto;
import gc.cafe.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductList());
    }
}
