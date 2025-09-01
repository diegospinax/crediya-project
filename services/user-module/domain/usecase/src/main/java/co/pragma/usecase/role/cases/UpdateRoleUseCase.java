package co.pragma.usecase.role.cases;

import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleId;
import reactor.core.publisher.Mono;

public interface UpdateRoleUseCase {
    Mono<Role> updateRole(RoleId roleId, Role roleNewData);
}
