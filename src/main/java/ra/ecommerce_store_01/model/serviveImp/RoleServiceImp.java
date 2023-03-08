package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.ERole;
import ra.ecommerce_store_01.model.entity.Roles;
import ra.ecommerce_store_01.model.service.RoleService;

import java.util.Optional;
@Service
public class RoleServiceImp implements RoleService {
    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return Optional.empty();
    }
}
