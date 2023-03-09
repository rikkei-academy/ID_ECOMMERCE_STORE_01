package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SignupRequest {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Date created;
    private boolean userStatus;
    private Set<String> listRoles;
}
