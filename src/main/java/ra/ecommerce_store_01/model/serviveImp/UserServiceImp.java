package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.repository.UserRepository;
import ra.ecommerce_store_01.model.service.UserService;

public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
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
