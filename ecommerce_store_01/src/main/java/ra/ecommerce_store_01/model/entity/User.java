package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "UserId")
    private int userId;
    @JoinColumn(name = "UserName",unique = true,nullable = false)
    private String userName;
    @JoinColumn(name = "PasswordUser")
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
    @JoinColumn(name = "RoleUser")
    private int roleUser;
}
