package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Star;

import java.util.List;

@Repository
public interface StarRepository extends JpaRepository<Star,Integer> {
    List<Star> findAllByProduct_ProductId(int productId);
}
