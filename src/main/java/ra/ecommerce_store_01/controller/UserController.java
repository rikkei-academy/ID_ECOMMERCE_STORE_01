package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.jwt.JwtTokenProvider;
import ra.ecommerce_store_01.model.entity.ERole;
import ra.ecommerce_store_01.model.entity.Roles;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.service.RoleService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.LoginRequest;
import ra.ecommerce_store_01.payload.request.ResetPasswordRequest;
import ra.ecommerce_store_01.payload.request.SignupRequest;
import ra.ecommerce_store_01.model.entity.PasswordResetToken;
import ra.ecommerce_store_01.model.sendEmail.ProvideSendEmail;
import ra.ecommerce_store_01.model.service.ForgotPassService;
import ra.ecommerce_store_01.payload.request.UserUpdate;
import ra.ecommerce_store_01.payload.respone.JwtResponse;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.payload.respone.UserReponse;
import ra.ecommerce_store_01.security.CustomUserDetails;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private ProvideSendEmail provideSendEmail;
    @Autowired
    private ForgotPassService forgotPassService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {

            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));

        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        User user = new User();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());


        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());

        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        System.out.println(strRoles.toString());


        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {

                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }



    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        boolean check = encoder.matches(resetPasswordRequest.getOldPassWord(), userDetails.getPassword());
        if (check) {
            if (resetPasswordRequest.getNewPassWord().equals(resetPasswordRequest.getConfirmNewPassWord())) {
                user.setPassword(encoder.encode(resetPasswordRequest.getNewPassWord()));
                userService.saveOrUpdate(user);
                return ResponseEntity.ok(new MessageResponse("Reset password successfully"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu mới không trùng khớp, vui lòng thử lại!"));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu cũ không đúng, vui lòng thử lại!"));
        }
    }


    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToken(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(),
                customUserDetail.getPhone(), listRoles));
    }


    @GetMapping("/forgotPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {
        System.out.println(userEmail);
        if (userService.existsByEmail(userEmail)) {
            User users = userService.findByEmail(userEmail);
            String token = UUID.randomUUID().toString();
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken(token);
            String mess = "token is valid for 5 minutes.\n" + "Your token: " + token;
            myToken.setUsers(users);
            Date now = new Date();
            myToken.setStartDate(now);
            forgotPassService.saveOrUpdate(myToken);
            provideSendEmail.sendSimpleMessage(users.getEmail(),
                    "Reset your password", mess);
            return ResponseEntity.ok("Email sent! Please check your email");
        } else {
            return new ResponseEntity<>(new MessageResponse("Email is not already"), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/creatNewPass")
    public ResponseEntity<?> creatNewPass(@RequestParam("token") String token, @RequestParam("userName") String userName, @RequestParam("newPassword") String newPassword) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>(new MessageResponse("token is fail"), HttpStatus.NO_CONTENT);
        } else {
            PasswordResetToken passwordResetToken = forgotPassService.getLastTokenByUserId(user.getUserId());
            long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
            long date2 = new Date().getTime();
            if (date2 > date1) {
                return new ResponseEntity<>(new MessageResponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
            } else {
                if (passwordResetToken.getToken().equals(token)) {
                    User user1 = userService.findByUserId(user.getUserId());
                    user.setPassword(encoder.encode(newPassword));
                    userService.saveOrUpdate(user1);
                    return new ResponseEntity<>(new MessageResponse("update password successfully "), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new MessageResponse("token is fail "), HttpStatus.NO_CONTENT);
                }
            }
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<UserReponse> list = userService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("softByName")
    public ResponseEntity<?> softByName(@RequestParam("direction") String direction,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        List<UserReponse> list = userService.softByName(direction, size, page);
        return ResponseEntity.ok(list);
    }

    @GetMapping("filterUser")
    public ResponseEntity<?> filterUser(@RequestParam("status") boolean status) {
        List<UserReponse> list = userService.filterUser(status);
        return ResponseEntity.ok(list);
    }



    @PutMapping("blockUser/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable("userId") int userId) {
        boolean check = userService.blockUser(userId);
        if (check) {
            return ResponseEntity.ok("Đã khóa tài khoản thành công !");
        } else {
            return ResponseEntity.ok("Khóa tài khoản thất bại !");
        }
    }

    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name) {
        List<UserReponse> list = userService.searchByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("pagination")
    public ResponseEntity<?> pagination(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page * size, size);
        Map<String, Object> list = userService.pagination(pageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam int userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdate userUpdate,@RequestParam int userId) {
        User user = userService.findByUserId(userId);
//        CustomUserDetails customUserDetail = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user.setUserName(userUpdate.getUserName());
            user.setFirstName(userUpdate.getFirstName());
            user.setLastName(userUpdate.getLastName());
            user.setEmail(userUpdate.getEmail());
            user.setPhone(userUpdate.getPhone());
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(user);
    }
}
