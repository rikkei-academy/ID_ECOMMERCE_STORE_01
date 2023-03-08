package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.jwt.JwtTokenProvider;
import ra.ecommerce_store_01.model.entity.ERole;
import ra.ecommerce_store_01.model.entity.PasswordResetToken;
import ra.ecommerce_store_01.model.entity.Roles;
import ra.ecommerce_store_01.model.entity.User;
import ra.ecommerce_store_01.model.sendEmail.ProvideSendEmail;
import ra.ecommerce_store_01.model.service.ForgotPassService;
import ra.ecommerce_store_01.model.service.RoleService;
import ra.ecommerce_store_01.model.service.UserService;
import ra.ecommerce_store_01.payload.request.LoginRequest;
import ra.ecommerce_store_01.payload.request.ResetPasswordRequest;
import ra.ecommerce_store_01.payload.request.SignUpRequest;
import ra.ecommerce_store_01.payload.respone.JwtResponse;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.security.CustomUserDetails;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth/user")
public class UserController {
    private ProvideSendEmail provideSendEmail;
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

    @PostMapping("signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already"));
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setPassword(encoder.encode(signUpRequest.getPassWord()));
        user.setEmail(signUpRequest.getEmail());
        user.setPhone(signUpRequest.getPhone());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setUserStatus(true);
        Set<String> strRoles = signUpRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{
                if (role.equals("admin")) {
                    Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                            .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                    listRoles.add(adminRole);
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
        if (userService.existsByUserName(resetPasswordRequest.getUserName())) {
            boolean check= encoder.matches(resetPasswordRequest.getOldPassWord(), userDetails.getPassword());
            if (check) {
                boolean checkNewPassword= encoder.matches(resetPasswordRequest.getOldPassWord(), resetPasswordRequest.getConfirmNewPassWord());
                if (checkNewPassword) {
                    return ResponseEntity.ok(new MessageResponse("Reset password successfully"));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu không trùng khớp, vui lòng thử lại!"));
                }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Mật khẩu không trùng khớp, vui lòng thử lại!"));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Tài khoản không tồn tại, vui lòng thử lại!"));
        }
    }
    @GetMapping("/forgotPassword")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) {
        User user = userService.findByUserName(userEmail);
        if (userService.existsByEmail(userEmail)) {
            User users = userService.findByEmail(userEmail);
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = UUID.randomUUID().toString();
            PasswordResetToken myToken = new PasswordResetToken();
            myToken.setToken( token);
            String mess= "token is valid for 5 minutes.\n"+"Your token: " +token;
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
    public ResponseEntity<?> creatNewPass(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PasswordResetToken passwordResetToken = forgotPassService.getLastTokenByUserId(userDetails.getUserId());
        long date1 = passwordResetToken.getStartDate().getTime() + 1800000;
        long date2 = new Date().getTime();
        if (date2 > date1) {
            return new ResponseEntity<>(new MessageResponse("Expired Token "), HttpStatus.EXPECTATION_FAILED);
        } else {
            if (passwordResetToken.getToken().equals(token)) {
                User users = userService.findByUserID(userDetails.getUserId());
                users.setPassword(encoder.encode(newPassword));
                userService.saveOrUpdate(users);
                return new ResponseEntity<>(new MessageResponse("update password successfully "), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageResponse("token is fail "), HttpStatus.NO_CONTENT);
            }
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToken(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,customUserDetail.getUsername(),customUserDetail.getEmail(),
                customUserDetail.getPhone(),listRoles));
    }
}
