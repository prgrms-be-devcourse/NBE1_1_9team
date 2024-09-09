package gc.cafe.model.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gc.cafe.controller.dto.res.OrderItemResDto;
import gc.cafe.controller.dto.res.OrderResDto;
import gc.cafe.controller.dto.res.ProductListDto;
import gc.cafe.model.repository.SelectOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gc.cafe.model.entity.QProducts.products;
import static gc.cafe.model.entity.QOrders.orders;
import static gc.cafe.model.entity.QOrderItems.orderItems;

@Repository
@RequiredArgsConstructor
public class SelectOrdersRepositoryImpl implements SelectOrdersRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderResDto> findOrdersByEmail(String email) {
        return queryFactory
                .select(Projections.constructor(OrderResDto.class,
                        orders.id,
                        orders.email,
                        orders.address,
                        orders.postcode,
                        orders.status,
                        orders.updatedAt.coalesce(orders.createdAt).as("orderDate"),
                        Projections.list(Projections.constructor(OrderItemResDto.class,
                                orderItems.seq,
                                Projections.constructor(ProductListDto.class,
                                        products.id,
                                        products.name,
                                        products.category,
                                        products.price),
                                orderItems.quantity)
                        )
                ))
                .from(orders)
                .leftJoin(orderItems).on(orderItems.orders.id.eq(orders.id))
                .leftJoin(products).on(orderItems.products.id.eq(products.id))
                .where(orders.email.eq(email))
                .fetch();
    }
}
