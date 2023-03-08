package ra.ecommerce_store_01.model.service;

import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.ERole;
import ra.ecommerce_store_01.model.entity.Roles;

import java.util.Optional;
@Service
public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
