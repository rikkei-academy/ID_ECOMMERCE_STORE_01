package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Brand;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
    List<Brand> findByBrandNameContaining(String name, Pageable pageable);
}
