package gc.cafe.controller;

import gc.cafe.controller.dto.DTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.ProductsRequestDTO;
import gc.cafe.controller.dto.common.statusdto.ErrorDTO;
import gc.cafe.controller.dto.common.statusdto.ResponseStatusDTO;
import gc.cafe.controller.dto.common.statusdto.SuccessDTO;
import gc.cafe.entity.Products;
import gc.cafe.service.ProductService;
import gc.cafe.util.UUIDConversion;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    //상품 등록 (중)
    //상품 수정 (중)
    //상품 단건 조회 (중)
    //상품 삭제 (중)
    @GetMapping
    public String productPage() {
        return "상품 관리 페이지";
    }

    @PostMapping
    public ResponseStatusDTO registerProduct(
            @Validated ProductsRequestDTO productsRequestDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ErrorDTO("404", "illegal form data");
        }
        Products newProduct = Products.getProductsFromRequestDTO(productsRequestDTO);
        productService.registerProduct(newProduct);
        return new SuccessDTO("200");
    }

    @GetMapping("/{productId}")
    public DTO findProduct(@PathVariable("productId") String productId) {
        Optional<Products> product = productService
                .findProduct(UUIDConversion.uuidToBytes(UUID.fromString(productId)));
        if (product.isEmpty()) {
            return new ErrorDTO("404", "not found product");
        }
        return Products.getResponseDTOFromProducts(product.get());
    }

    @PutMapping("/{productId}")
    public ResponseStatusDTO updateProduct(@PathVariable("productId") String productId,
                             @Validated ProductsRequestDTO productsRequestDTO, BindingResult result) {
        byte[] productIdToUpdate = UUIDConversion.uuidToBytes(UUID.fromString(productId));
        if (result.hasErrors()) {
            return new ErrorDTO("404", "illegal form data");
        }
        if (productService.findProduct(productIdToUpdate).isEmpty()) {
            return new ErrorDTO("404", "not found product");
        }
        Products newProduct = Products.getProductsFromRequestDTO(productsRequestDTO);
        productService.updateProduct(productIdToUpdate, newProduct);

        return new SuccessDTO("200");
    }

    @DeleteMapping("/{productId}")
    public ResponseStatusDTO removeProduct(@PathVariable("productId") String productId) {
        byte[] productIdToUpdate = UUIDConversion.uuidToBytes(UUID.fromString(productId));

        if (productService.findProduct(productIdToUpdate).isEmpty()) {
            return new ErrorDTO("404", "not found product");
        }
        productService.removeProduct(productIdToUpdate);
        return new SuccessDTO("200");
    }
}
