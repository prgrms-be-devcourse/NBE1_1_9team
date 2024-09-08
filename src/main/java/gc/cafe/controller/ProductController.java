package gc.cafe.controller;

import gc.cafe.domain.dto.Response;
import gc.cafe.domain.dto.request.ProductRequestDto;
import gc.cafe.domain.dto.response.ProductResponseDto;
import gc.cafe.domain.entity.Product;
import gc.cafe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Response<Long>> save(@RequestBody ProductRequestDto requestDto) {
        Long productId = productService.save(requestDto);
        return ResponseEntity.ok().body(Response.success(productId));
    }

    @GetMapping("/list")
    public ResponseEntity<Response<List<ProductResponseDto>>> list() {
        List<Product> products = productService.list();
        List<ProductResponseDto> responseDtos = products.stream()
                .map(ProductResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(Response.success(responseDtos));
    }

    @GetMapping("/read")
    public ResponseEntity<Response<ProductResponseDto>> read(@RequestParam Long productId) {
        Product product = productService.findProductById(productId);
        ProductResponseDto responseDto = new ProductResponseDto(product);
        return ResponseEntity.ok().body(Response.success(responseDto));
    }


    @PutMapping("/update")
    public ResponseEntity<Response<Long>> update(@RequestParam Long productId,
                                                 @RequestBody ProductRequestDto requestDto) {
        Long updateId = productService.update(productId, requestDto);
        return ResponseEntity.ok().body(Response.success(updateId));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
