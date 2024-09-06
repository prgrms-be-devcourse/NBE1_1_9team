package gc.cafe.model.service;

import gc.cafe.model.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getProductList();

    String addProduct(ProductDto dto);
}
