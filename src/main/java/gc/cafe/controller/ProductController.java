package gc.cafe.controller;

import gc.cafe.controller.dto.req.ProductReqDto;
import gc.cafe.controller.dto.res.ProductListDto;
import gc.cafe.controller.dto.res.ProductResDto;
import gc.cafe.model.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 목록 조회
     * @param page 페이지 번호
     * @return
     */
    @GetMapping("")
    public ResponseEntity<List<ProductListDto>> getProductList(@RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(productService.getProductList(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResDto> getProductOne(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.getProductOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductReqDto dto) {
        return ResponseEntity.ok().body(productService.addProduct(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductReqDto dto) {
        return ResponseEntity.ok().body(productService.updateProduct(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.deleteProduct(id));
    }
}
