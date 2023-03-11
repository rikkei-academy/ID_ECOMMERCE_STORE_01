package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.ProductLocation;

import java.util.List;

@Repository
public interface ProductLocationRepository extends JpaRepository<ProductLocation,Integer> {
    List<ProductLocation> findAllByLocation_LocationId(int locationId);
}
