package gc.cafe.api.service.product;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        Product saveProduct = productRepository.save(request.toEntity());
        return ProductResponse.of(saveProduct);
    }

    @Override
    public Long deleteProduct(Long id) {
        return 0L;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateServiceRequest request) {
        return null;
    }

    @Override
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(()->new NoSuchElementException("해당 " + id + "를 가진 상품을 찾을 수 없습니다."));
        return ProductResponse.of(product);
    }
}
