package gc.cafe.model.repository;

import gc.cafe.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, byte[]> {

    List<Products> findAllByIdIn(@Param("product_id") List<byte[]> ids);
}
