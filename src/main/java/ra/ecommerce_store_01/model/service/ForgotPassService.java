package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.PasswordResetToken;

public interface ForgotPassService {
    PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken);
    PasswordResetToken getLastTokenByUserId(int userId);
}
