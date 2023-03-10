package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    User findByUserId(int userId);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findAllByUserStatus(boolean status);
    @Query(value = "select u.UserID,u.UserName,u.FirstName,u.LastName,u.Phone,u.Email,u.UserStatus,u.password\n" +
            "            from user u\n" +
            "                     group by UserID\n" +
            "            order by FirstName asc  limit :size offset :page",nativeQuery = true)
    List<User> softByNameASC(@Param("size") int size, @Param("page") int page);

    @Query(value = "select u.UserID,u.UserName,u.FirstName,u.LastName,u.Phone,u.Email,u.UserStatus,u.password\n" +
            "            from user u\n" +
            "                     group by UserID\n" +
            "            order by FirstName desc  limit :size offset :page",nativeQuery = true)
    List<User> softByNameDESC(@Param("size") int size, @Param("page") int page);
    @Query(value = "select ceil(count(u.UserID)/:size) from user u ",nativeQuery = true)
    int totalPage(@Param("size") int size);

    List<User> searchByFirstNameContaining(String name);
    User findByEmail(String email);
}
