package ra.ecommerce_store_01.payload.respone;

import lombok.Data;

@Data
public class UserReponse {
    private int userId;
    private String userName;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private boolean userStatus;
}
