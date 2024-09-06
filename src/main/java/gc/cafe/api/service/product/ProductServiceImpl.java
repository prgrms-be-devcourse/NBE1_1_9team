package gc.cafe.api.service.product;

import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        return null;
    }

    @Override
    public Long deleteProduct(Long id) {
        return 0L;
    }
}
