package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.repository.UserRepository;
import ra.ecommerce_store_01.model.service.UserService;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUserID(int userID) {
        return userRepository.findByUserId(userID);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByUserName(email);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }
}
