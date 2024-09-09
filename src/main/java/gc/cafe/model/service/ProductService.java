package gc.cafe.model.service;

import gc.cafe.controller.dto.req.ProductReqDto;
import gc.cafe.controller.dto.res.ProductListDto;
import gc.cafe.controller.dto.res.ProductResDto;
import gc.cafe.model.entity.Products;

import java.util.List;

public interface ProductService {

    List<ProductListDto> getProductList(int page);

    String addProduct(ProductReqDto dto);

    String updateProduct(ProductReqDto dto);

    String deleteProduct(String id);

    ProductResDto getProductOne(String id);
}
