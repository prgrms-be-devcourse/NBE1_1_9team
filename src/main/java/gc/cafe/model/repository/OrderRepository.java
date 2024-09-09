package gc.cafe.model.repository;

import gc.cafe.model.entity.OrderStatus;
import gc.cafe.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, byte[]> {

    @Modifying
    @Query("UPDATE Orders o SET o.status = :status, o.updatedAt = local datetime WHERE o.status = 'REQUESTED'")
    void updateAllByStatusRequestedEquals(OrderStatus status);
}
