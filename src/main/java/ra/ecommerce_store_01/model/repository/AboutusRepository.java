package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.AboutUs;

@Repository
public interface AboutusRepository extends JpaRepository<AboutUs,Integer> {
}
