package gc.cafe.product.repository;

import gc.cafe.product.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.productName = :productName")
    Optional<Product> findByProductName(@Param("productName") String productName);
}
