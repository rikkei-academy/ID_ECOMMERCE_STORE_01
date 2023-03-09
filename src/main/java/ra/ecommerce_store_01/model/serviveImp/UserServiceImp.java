package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.repository.UserRepository;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.respone.UserReponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByUserId(int userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<UserReponse> findAll() {
        List<User> listUser = userRepository.findAll();
        List<UserReponse> list = new ArrayList<>();
        for (User user :listUser) {
            UserReponse userReponse = new UserReponse();
            userReponse.setUserId(user.getUserId());
            userReponse.setUserName(user.getUserName());
            userReponse.setPhone(user.getPhone());
            userReponse.setEmail(user.getEmail());
            userReponse.setFirstName(user.getFirstName());
            userReponse.setLastName(user.getLastName());
            userReponse.setUserStatus(user.isUserStatus());
            list.add(userReponse);
        }

        return list;
    }

    @Override
    public UserReponse findById(int id) {
        User user = userRepository.findById(id).get();
        UserReponse userReponse = new UserReponse();
        userReponse.setUserId(user.getUserId());
        userReponse.setUserName(user.getUserName());
        userReponse.setPhone(user.getPhone());
        userReponse.setEmail(user.getEmail());
        userReponse.setFirstName(user.getFirstName());
        userReponse.setLastName(user.getLastName());
        userReponse.setUserStatus(user.isUserStatus());
        return userReponse;

    }


    @Override
    public List<UserReponse> filterUser(boolean status) {
        List<User> listUser = userRepository.findAllByUserStatus(status);
        List<UserReponse> list = new ArrayList<>();
        for (User user :listUser) {
            UserReponse userReponse = new UserReponse();
            userReponse.setUserId(user.getUserId());
            userReponse.setUserName(user.getUserName());
            userReponse.setPhone(user.getPhone());
            userReponse.setEmail(user.getEmail());
            userReponse.setFirstName(user.getFirstName());
            userReponse.setLastName(user.getLastName());
            userReponse.setUserStatus(user.isUserStatus());
            list.add(userReponse);
        }
        return list;
    }

    @Override
    public List<UserReponse> softByName(String directer, int size, int page) {
        List<User> listUser = new ArrayList<>();
        if (directer.equalsIgnoreCase("asc")){
            listUser = userRepository.softByNameASC(size,size*page);
        }else {
            listUser = userRepository.softByNameDESC(size,size*page);
        }
        List<UserReponse> list = new ArrayList<>();
        for (User user :listUser) {
            UserReponse userReponse = new UserReponse();
            userReponse.setUserId(user.getUserId());
            userReponse.setUserName(user.getUserName());
            userReponse.setPhone(user.getPhone());
            userReponse.setEmail(user.getEmail());
            userReponse.setFirstName(user.getFirstName());
            userReponse.setLastName(user.getLastName());
            userReponse.setUserStatus(user.isUserStatus());
            list.add(userReponse);
        }
        return list;

    }

    @Override
    public Map<String, Object> pagination(Pageable pageable) {
        Page<User> listUser = userRepository.findAll(pageable);
        List<UserReponse> list = new ArrayList<>();
        for (User user :listUser) {
            UserReponse userReponse = new UserReponse();
            userReponse.setUserId(user.getUserId());
            userReponse.setUserName(user.getUserName());
            userReponse.setPhone(user.getPhone());
            userReponse.setEmail(user.getEmail());
            userReponse.setFirstName(user.getFirstName());
            userReponse.setLastName(user.getLastName());
            userReponse.setUserStatus(user.isUserStatus());
            list.add(userReponse);
        }
        int totalPage = userRepository.totalPage(pageable.getPageSize());
        Map<String,Object> pagging = new HashMap<>();
        pagging.put("ListUser",list);
        pagging.put("totalPage",totalPage);
        return pagging;

    }

    @Override
    public boolean blockUser(int id) {
        User user = userRepository.findById(id).get();
        user.setUserStatus(false);
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<UserReponse> searchByName(String name) {
        List<User> listUser = userRepository.searchByFirstNameContaining(name);
        List<UserReponse> list = new ArrayList<>();
        for (User user :listUser) {
            UserReponse userReponse = new UserReponse();
            userReponse.setUserId(user.getUserId());
            userReponse.setUserName(user.getUserName());
            userReponse.setPhone(user.getPhone());
            userReponse.setEmail(user.getEmail());
            userReponse.setFirstName(user.getFirstName());
            userReponse.setLastName(user.getLastName());
            userReponse.setUserStatus(user.isUserStatus());
            list.add(userReponse);
        }
        return list;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }
}
