package gc.cafe.repository;

import gc.cafe.domain.entity.Order;
import gc.cafe.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void deleteByOrder(Order order);
}
