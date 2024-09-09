package gc.cafe.repository;

import gc.cafe.entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface OrdersRepository extends JpaRepository<Orders, byte[]> {
    public List<Orders> findByEmail(String email);
}
