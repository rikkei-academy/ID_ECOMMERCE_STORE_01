package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Roles;
@Repository
public interface RoleRepository extends JpaRepository<Roles,Integer> {
}
