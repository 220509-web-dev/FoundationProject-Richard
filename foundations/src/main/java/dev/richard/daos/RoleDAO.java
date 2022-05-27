package dev.richard.daos;
import dev.richard.entities.Role;

import java.util.List;

public interface RoleDAO {
    Role createRole(Role role);
    Role getRoleById(int id);
    List getAllRoles();
    Role updateRole(Role role);
    Role deleteRoleById(Role role);
}
