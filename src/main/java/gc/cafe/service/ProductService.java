package gc.cafe.service;

import gc.cafe.domain.dto.request.ProductRequestDto;
import gc.cafe.domain.entity.Category;
import gc.cafe.domain.entity.Product;
import gc.cafe.exception.AppException;
import gc.cafe.exception.ErrorCode;
import gc.cafe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Long save(ProductRequestDto requestDto) {
        Product product = requestDto.toEntity();
        return productRepository.save(product).getProductId();
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
    }

    public Long update(Long productId, ProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        product.update(
                requestDto.getProductName(),
                requestDto.getCategory(),
                requestDto.getPrice(),
                requestDto.getDescription()
        );
        return productRepository.save(product).getProductId();

    }

    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_PRODUCT));
        productRepository.delete(product);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
