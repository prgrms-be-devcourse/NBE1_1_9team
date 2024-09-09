package gc.cafe.model.repository;

import gc.cafe.controller.dto.res.ProductListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SelectProductsRepository {

    List<ProductListDto> findSimpleData(Pageable pageable);
}
