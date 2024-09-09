package gc.cafe.repository;

import gc.cafe.entity.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ProductsRepository extends JpaRepository<Products, byte[]> {
}
