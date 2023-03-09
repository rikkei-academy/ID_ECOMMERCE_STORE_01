package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.PasswordResetToken;
import ra.ecommerce_store_01.model.repository.ForgotPassRepository;
import ra.ecommerce_store_01.model.service.ForgotPassService;
@Service
public class ForgotPassImp implements ForgotPassService {
    @Autowired
    private ForgotPassRepository forgotPassRepository;
    @Override
    public PasswordResetToken saveOrUpdate(PasswordResetToken passwordResetToken) {
        return forgotPassRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken getLastTokenByUserId(int userId) {
        return forgotPassRepository.getLastTokenByUserId(userId);
    }
}
