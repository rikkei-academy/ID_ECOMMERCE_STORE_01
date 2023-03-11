package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.Set;

@Data
public class UserUpdate {


    private String userName;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
