package gc.cafe.model.service;

import gc.cafe.controller.dto.req.ProductReqDto;
import gc.cafe.controller.dto.res.ProductListDto;
import gc.cafe.controller.dto.res.ProductResDto;
import gc.cafe.model.entity.Products;
import gc.cafe.model.repository.ProductRepository;
import gc.cafe.model.repository.SelectProductsRepository;
import gc.cafe.util.uuid.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final SelectProductsRepository selectRepository;
    private final int LIST_SIZE = 8;

    @Override
    public List<ProductListDto> getProductList(int page) {
        Pageable pageable = getPageable(page);
        return selectRepository.findSimpleData(pageable);
    }

    @Override
    public String addProduct(ProductReqDto dto) {
        productRepository.save(ProductReqDto.createWithNewId(dto));
        return "저장완료";
    }

    @Override
    public String updateProduct(ProductReqDto dto) {
        productRepository.save(ProductReqDto.createWithExistingId(dto));
        return "수정완료";
    }

    @Override
    public String deleteProduct(String id) {
        productRepository.deleteById(UuidUtils.convertStringToBytes(id));
        return "삭제 완료";
    }

    @Override
    public ProductResDto getProductOne(String id) {
        byte[] bytesId = UuidUtils.convertStringToBytes(id);
        Products findProduct = productRepository.findById(bytesId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        return Products.toResDto(findProduct);
    }

    private Pageable getPageable(int page) {
        return PageRequest.of(page-1, LIST_SIZE, Sort.Direction.DESC, "created_at");
    }
}
