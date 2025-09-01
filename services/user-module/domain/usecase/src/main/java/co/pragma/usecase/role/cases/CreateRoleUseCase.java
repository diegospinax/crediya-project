package co.pragma.usecase.role.cases;

import co.pragma.model.role.Role;
import reactor.core.publisher.Mono;

public interface CreateRoleUseCase {
    Mono<Role> createRole(Role role);
}
