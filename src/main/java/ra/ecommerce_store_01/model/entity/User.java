package ra.ecommerce_store_01.model.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "UserId")
    private int userId;
    @JoinColumn(name = "UserName",unique = true,nullable = false)
    private String userName;
    @JoinColumn(name = "password")
    private String password;
    @JoinColumn(name = "FirstName")
    private String firstName;
    @JoinColumn(name = "LastName")
    private String lastName;
    @JoinColumn(name = "Email",nullable = false,unique = true)
    private String email;
    @JoinColumn(name = "Phone")
    private String phone;
    @JoinColumn(name = "UserStatus")
    private boolean userStatus;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role",joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    private Set<Roles> listRoles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Star> listStar = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "wishlist",joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private Set<Product> wishList = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<Orders> listOrder = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Review> listReview = new ArrayList<>();
}
