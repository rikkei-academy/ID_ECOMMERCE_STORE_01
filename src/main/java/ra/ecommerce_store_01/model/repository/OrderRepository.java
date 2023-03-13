package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Orders;
@Repository
public interface OrderRepository extends JpaRepository<Orders,Integer> {
    Page<Orders> findAllByOrderStatus(int status, Pageable pageable);
    Page<Orders> findAllByUser_UserId(int id,Pageable pageable);

    Page<Orders> findAllByUser_UserIdAndOrderStatus(int userId,int status,Pageable pageable);
    Orders findByUser_UserIdAndOrderStatus(int id,int status);
}

