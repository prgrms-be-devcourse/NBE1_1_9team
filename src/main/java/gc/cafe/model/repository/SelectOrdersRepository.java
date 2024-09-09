package gc.cafe.model.repository;

import gc.cafe.controller.dto.res.OrderResDto;

import java.util.List;

public interface SelectOrdersRepository {

    List<OrderResDto> findOrdersByEmail(String email);
}
