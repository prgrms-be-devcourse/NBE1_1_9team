package gc.cafe.api.controller.product;

import gc.cafe.api.ApiResponse;
import gc.cafe.api.controller.product.request.ProductCreateRequest;
import gc.cafe.api.service.product.ProductService;
import gc.cafe.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(productService.createProduct(request.toServiceRequest())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Long>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(productService.deleteProduct(id)));
    }

}
