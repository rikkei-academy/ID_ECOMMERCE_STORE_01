package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdate {
    private int userId;
    private String password;
    private String userName;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private boolean userStatus;
    private Set<String> listRoles;
}
