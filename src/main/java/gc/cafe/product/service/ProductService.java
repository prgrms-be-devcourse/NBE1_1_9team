package gc.cafe.product.service;

import gc.cafe.product.dto.ProductAddRequest;
import gc.cafe.product.dto.ProductDetailResponse;
import gc.cafe.product.dto.ProductListResponse;
import gc.cafe.product.dto.ProductResponse;
import gc.cafe.product.dto.ProductSimpleResponse;
import gc.cafe.product.dto.ProductUpdateRequest;
import gc.cafe.product.entity.Product;
import gc.cafe.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse addProduct(ProductAddRequest request) {
        Product product = new Product(
                request.name(),
                request.category(),
                request.price(),
                request.description()
        );
        Product savedProduct = productRepository.save(product);
        return new ProductResponse(savedProduct.getId());
    }

    public ProductListResponse getAllProduct() {
        List<ProductSimpleResponse> result = productRepository.findAll().stream()
                .map(it -> new ProductSimpleResponse(it.getProductName(), it.getCategory(), it.getPrice()))
                .toList();
        return new ProductListResponse(result);
    }

    public ProductDetailResponse getProductDetail(Long productId) {
        Product product = getProduct(productId);
        return new ProductDetailResponse(product.getProductName(), product.getCategory(), product.getPrice(), product.getDescription());
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = getProduct(productId);
        product.update(request);
        return new ProductResponse(product.getId());
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다."));
    }

}
