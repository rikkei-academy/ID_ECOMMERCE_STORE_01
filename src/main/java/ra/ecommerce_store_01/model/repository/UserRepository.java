package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByUserId(int userID);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
