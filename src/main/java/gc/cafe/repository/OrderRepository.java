package gc.cafe.repository;

import gc.cafe.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByEmail(String email);

    @Modifying(flushAutomatically = true)
    @Query("update Order o set o.orderStatus ='COMPLETED' where o.orderStatus = 'READY'")
    void bulkUpdateOrderStatus();
}
