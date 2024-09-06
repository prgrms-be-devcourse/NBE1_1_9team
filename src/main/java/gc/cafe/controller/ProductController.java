package gc.cafe.controller;

import gc.cafe.model.dto.ProductDto;
import gc.cafe.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok().body(productService.getProductList());
    }

    @PostMapping("/products/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok().body(productService.addProduct(dto));
    }

    @PutMapping("/product/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok().body(productService.updateProduct(dto));
    }
}
