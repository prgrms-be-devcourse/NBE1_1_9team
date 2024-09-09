package gc.cafe.model.repository;

import gc.cafe.model.entity.OrderItems;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BulkOrderItemJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private static final int BATCH_SIZE = 100;

    @Transactional
    public void saveAll(List<OrderItems> orderItemsList) {
        for (int i = 0; i < orderItemsList.size(); i++) {
            entityManager.persist(orderItemsList.get(i));
            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.flush();
        entityManager.clear();
    }
}

