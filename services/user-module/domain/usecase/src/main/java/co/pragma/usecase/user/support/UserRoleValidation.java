package co.pragma.usecase.user.support;

import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.User;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import reactor.core.publisher.Mono;

public class UserRoleValidation {

    private final RoleRepository roleRepository;

    public UserRoleValidation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Mono<User> validateRoleExists(User user) {
        return roleRepository.findById(user.roleId())
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role does not exists.")))
                .thenReturn(user);
    }
}
