package ra.ecommerce_store_01.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
//    @Column(name = "Created")
//    @JsonFormat(pattern = "dd/MM/yyyy")
//    private Date created;
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
}
