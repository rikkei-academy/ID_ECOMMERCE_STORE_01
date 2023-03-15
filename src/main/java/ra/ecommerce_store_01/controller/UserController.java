package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.jwt.JwtTokenProvider;
import ra.ecommerce_store_01.model.entity.*;
import ra.ecommerce_store_01.model.service.RoleService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.LoginRequest;


import ra.ecommerce_store_01.payload.respone.*;

import ra.ecommerce_store_01.payload.request.ResetPasswordRequest;
import ra.ecommerce_store_01.payload.request.SignupRequest;
import ra.ecommerce_store_01.model.sendEmail.ProvideSendEmail;
import ra.ecommerce_store_01.model.service.ForgotPassService;
import ra.ecommerce_store_01.payload.request.UserUpdate;
import ra.ecommerce_store_01.payload.respone.JwtResponse;

import ra.ecommerce_store_01.security.CustomUserDetails;
import ra.ecommerce_store_01.security.CustomUserDetailsService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    public static final String FORMAT_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,39}$";
    public static boolean checkPassword(String password) {
        Pattern pattern = Pattern.compile(FORMAT_PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

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
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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
        if (checkPassword(signupRequest.getPassword())) {
            user.setPassword(encoder.encode(signupRequest.getPassword()));
        } else {
            return ResponseEntity.badRequest().body("Mật khẩu phải gồm 6 ký tự trở lên, gồm ký tự thường, hoa và số");
        }
        user.setEmail(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();

        if (strRoles == null) {
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }


    @PatchMapping("resetPassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        boolean check = encoder.matches(resetPasswordRequest.getOldPassWord(), userDetails.getPassword());
        if (check) {
            if (checkPassword(resetPasswordRequest.getNewPassWord())) {
                if (resetPasswordRequest.getNewPassWord().equals(resetPasswordRequest.getConfirmNewPassWord())) {
                    user.setPassword(encoder.encode(resetPasswordRequest.getNewPassWord()));
                    userService.saveOrUpdate(user);
                    return ResponseEntity.ok(new MessageResponse("Reset password successfully"));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu mới không trùng khớp, vui lòng thử lại!"));
                }
            } else {
                return ResponseEntity.badRequest().body("Mật khẩu phải gồm 6 ký tự trở lên, gồm ký tự thường, hoa và số");
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) {
        if (userService.existsByEmail(userEmail)) {
            User users = userService.findByEmail(userEmail);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(users.getUserName());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
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

    @PatchMapping("/creatNewPass")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> creatNewPass(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordResetToken passwordResetToken = forgotPassService.getLastTokenByUserId(userDetails.getUserId());
        long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
        long date2 = new Date().getTime();
        if (date2 > date1) {
            return new ResponseEntity<>(new MessageResponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
        } else {
            if (passwordResetToken.getToken().equals(token)) {
                User users = userService.findByUserId(userDetails.getUserId());
                if (checkPassword(newPassword)) {
                    users.setPassword(encoder.encode(newPassword));
                } else {
                    return ResponseEntity.badRequest().body("Mật khẩu phải gồm 6 ký tự trở lên, gồm ký tự thường, hoa và số");
                }
                userService.saveOrUpdate(users);
                return new ResponseEntity<>(new MessageResponse("update password successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("token is fail "), HttpStatus.NO_CONTENT);
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


    @PatchMapping("blockUser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdate userUpdate, @RequestParam int userId) {
        User user = userService.findByUserId(userId);
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setEmail(userUpdate.getEmail());
        user.setPhone(userUpdate.getPhone());
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logOut")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> logOut(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        // Clear the authentication from server-side (in this case, Spring Security)
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("You have been logged out.");
    }

    /*
        ADD TO WISHLIST - add a product to WishList
        input value: Integer productId
        output value: true/false
        tin
     */
    @PatchMapping("addToWishList/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToWishList(@PathVariable("productId") int proId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean check = userService.addOrRemoteWishList(userDetails.getUserId(), proId, "add");
        if (check) {
            return ResponseEntity.ok("Đã thêm sản phẩm vào danh sách yêu thích thành công ! ");
        } else {
            return ResponseEntity.ok("Thêm sản phẩm vào yêu thích thất bại! ");
        }
    }


    /*
       REMOTE WISHLIST - remote a product to WishList
       input value: Integer productId
       output value: true/false
       tin
    */
    @PatchMapping("remoteWishList/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> remoteWishList(@PathVariable("productId") int proId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean check = userService.addOrRemoteWishList(userDetails.getUserId(), proId, "remote");
        if (check) {
            return ResponseEntity.ok("Đã xóa sản phẩm khỏi danh sách yêu thích thành công ! ");
        } else {
            return ResponseEntity.ok("Xóa sản phẩm vào yêu thích thất bại! Hoặc chưa có sản phẩm trong danh sách yêu thích! ");
        }
    }

    //=================================================
    /*
       FIND ALL WISHLISt - Find all products in the user's wishlist.
       output value: List<Product>
       tin
    */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("findAllWishList")
    public ResponseEntity<?> findAllWishList() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserId(userDetails.getUserId());
        List<ProductResponse> list = new ArrayList<>();
        for (Product pr : user.getWishList()) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(pr.getProductId());
            productResponse.setProductName(pr.getProductName());
            productResponse.setDelivery(pr.isDelivery());
            productResponse.setImageLink(pr.getImageLink());
            productResponse.setPrice(pr.getPrice());
            productResponse.setDescription(pr.getDescription());
            productResponse.setBrandId(pr.getBrand().getBrandId());
            productResponse.setBrandName(pr.getBrand().getBrandName());
            productResponse.setCatalogId(pr.getCatalog().getCatalogId());
            productResponse.setCatalogName(pr.getCatalog().getCatalogName());
            productResponse.setViews(pr.getViews());
            for (Image image : pr.getListImage()) {
                productResponse.getListImage().add(image.getImageLink());
            }
            list.add(productResponse);
        }
        return ResponseEntity.ok(list);
    }
}
