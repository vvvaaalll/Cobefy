package hr.cobenco.Cobefy.repository;

import hr.cobenco.Cobefy.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(final String name);
}

