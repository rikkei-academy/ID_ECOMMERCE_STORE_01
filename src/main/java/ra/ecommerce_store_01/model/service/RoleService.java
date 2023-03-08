package ra.ecommerce_store_01.model.service;


import ra.ecommerce_store_01.model.entity.ERole;
import ra.ecommerce_store_01.model.entity.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
