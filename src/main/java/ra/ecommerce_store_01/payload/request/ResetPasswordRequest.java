package ra.ecommerce_store_01.payload.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    public String oldPassWord;
    private String newPassWord;
    public String confirmNewPassWord;
}
