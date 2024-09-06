package gc.cafe.product.controller;

import gc.cafe.base.dto.CommonResponse;
import gc.cafe.base.dto.EmptyDto;
import gc.cafe.product.dto.ProductAddRequest;
import gc.cafe.product.dto.ProductDetailResponse;
import gc.cafe.product.dto.ProductListResponse;
import gc.cafe.product.dto.ProductResponse;
import gc.cafe.product.dto.ProductUpdateRequest;
import gc.cafe.product.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public CommonResponse<ProductResponse> addProduct(@RequestBody ProductAddRequest productAddRequest) {
        return new CommonResponse<>(productService.addProduct(productAddRequest));
    }

    @GetMapping
    public CommonResponse<ProductListResponse> getAllProduct() {
        return new CommonResponse<>(productService.getAllProduct());
    }

    @GetMapping("/{productId}")
    public CommonResponse<ProductDetailResponse> getProductDetail(@PathVariable("productId") Long productId) {
        return new CommonResponse<>(productService.getProductDetail(productId));
    }

    @PutMapping("/{productId}")
    public CommonResponse<ProductResponse> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateRequest productUpdateRequest
    ) {
        return new CommonResponse<>(productService.updateProduct(productId, productUpdateRequest));
    }

    @DeleteMapping("/{productId}")
    public CommonResponse<EmptyDto> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return CommonResponse.EMPTY;
    }

}
