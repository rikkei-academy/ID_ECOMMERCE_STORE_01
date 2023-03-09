package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.payload.respone.UserReponse;

import java.util.List;
import java.util.Map;


public interface UserService {
    User findByUserName(String userName);
    User findByUserId(int userId);
    User findByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User saveOrUpdate(User user);
    List<UserReponse> findAll();
    UserReponse findById(int id);
    List<UserReponse> filterUser(boolean status);
    List<UserReponse> softByName(String directer,int size, int page);
    Map<String,Object> pagination(Pageable pageable);
    boolean blockUser(int id);
    List<UserReponse> searchByName(String name);
    User getUserById(int id);
}