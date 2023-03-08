package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String userName;
    private String passWord;
    private String email;
    private String Phone;
    private String firstName;
    private String lastName;
    private Set<String> listRoles;
}
