package co.pragma.usecase.role.cases;

import co.pragma.model.role.valueObject.RoleId;
import reactor.core.publisher.Mono;

public interface DeleteRoleUseCase {
    Mono<Void> deleteRole(RoleId roleId);
}
