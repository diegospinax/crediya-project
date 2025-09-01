package co.pragma.usecase.role.cases;

import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindRoleUseCase {
    Flux<Role> findAll();
    Mono<Role> findById(RoleId roleId);
    Mono<Role> findByName(RoleName name);
}
