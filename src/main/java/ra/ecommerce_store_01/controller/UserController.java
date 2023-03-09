package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ra.ecommerce_store_01.payload.request.SignupRequest;
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
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        System.out.println(strRoles.toString());
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{

                switch (role){
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
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
    @PostMapping("/signin")
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
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        List<UserReponse> list = userService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("softByName")
    public ResponseEntity<?> softByName(@RequestParam("direction")String direction,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        List<UserReponse> list = userService.softByName(direction,size,page);
        return ResponseEntity.ok(list);
    }
    @GetMapping("filterUser")
    public ResponseEntity<?> filterUser(@RequestParam("status")boolean status){
        List<UserReponse> list = userService.filterUser(status);
        return ResponseEntity.ok(list);
    }

    @PutMapping("blockUser/{userId}")
    public ResponseEntity<?> blockUser(@PathVariable("userId") int userId){
        boolean check = userService.blockUser(userId);
        if (check){
            return ResponseEntity.ok("Đã khóa tài khoản thành công !");
        }else {
            return ResponseEntity.ok("Khóa tài khoản thất bại !");
        }
    }

    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName(@RequestParam("name") String name){
        List<UserReponse> list = userService.searchByName(name);
        return ResponseEntity.ok(list);
    }
    @GetMapping("pagination")
    public ResponseEntity<?> pagination( @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page*size,size);
        Map<String, Object> list = userService.pagination(pageable);
        return ResponseEntity.ok(list);
    }
}
