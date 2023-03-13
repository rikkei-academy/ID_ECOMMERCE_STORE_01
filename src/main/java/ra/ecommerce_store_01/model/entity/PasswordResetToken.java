package ra.ecommerce_store_01.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User users;
    private Date startDate;
}