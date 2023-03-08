package ra.ecommerce_store_01.model.service;

import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.User;

public interface UserService {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByUserID(int userID);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdate(User user);
}