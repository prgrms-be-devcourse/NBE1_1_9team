package gc.cafe.order.repository;

import gc.cafe.order.domain.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.product where o.email = :email")
    List<Order> findByEmail(@Param("email") String email);

    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.product where o.id = :orderId")
    Optional<Order> findByIdWithOrOrderItems(@Param("orderId") Long orderId);
}
