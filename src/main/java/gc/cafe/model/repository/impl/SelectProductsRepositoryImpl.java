package gc.cafe.model.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gc.cafe.controller.dto.res.ProductListDto;
import gc.cafe.model.repository.SelectProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static gc.cafe.model.entity.QProducts.products;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SelectProductsRepositoryImpl implements SelectProductsRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductListDto> findSimpleData(Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(ProductListDto.class,
                        products.id,
                        products.name,
                        products.category,
                        products.price))
                .from(products)
                .offset(pageable.getOffset())        // 페이지의 시작 위치
                .limit(pageable.getPageSize())       // 한 페이지에 표시할 데이터 수
                .fetch();
    }


}
