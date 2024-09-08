package gc.cafe.repository;

import gc.cafe.domain.entity.Order;
import gc.cafe.domain.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmail(String email);

    @Modifying
    @Query("update Order o set o.orderStatus ='COMPLETED' where o.orderStatus = 'READY'")
    int bulkUpdateOrderStatus();
}
