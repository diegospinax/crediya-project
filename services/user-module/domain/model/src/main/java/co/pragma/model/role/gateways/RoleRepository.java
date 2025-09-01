package co.pragma.model.role.gateways;

import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Mono<Role> createRole(Role role);
    Flux<Role> findAll();
    Mono<Role> findById(RoleId roleId);
    Mono<Role> findByName(RoleName name);
    Mono<Role> updateRole(Role role);
    Mono<Void> deleteRole(RoleId roleId);
}
