package co.pragma.usecase.role.support;

import co.pragma.model.role.Role;
import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.User;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import reactor.core.publisher.Mono;

public class RoleUpdateHelper {

    private final RoleRepository roleRepository;

    public RoleUpdateHelper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role updateFields (Role newRole, Role currentRole) {
        return new Role(
                currentRole.id(),
                newRole.name() != null ? newRole.name() : currentRole.name(),
                newRole.description() != null ? newRole.description() : currentRole.description()
        );
    }

    public Mono<Role> validateNameChange(Role roleUpdate, Role currentRole) {
        if (roleUpdate.name() == null || roleUpdate.name().value.equals(currentRole.name().value)) {
            return Mono.just(roleUpdate);
        }
        return roleRepository.findByName(roleUpdate.name())
                .hasElement()
                .flatMap(exists -> {
                    if (exists)
                        return Mono.error(new DataIntegrationValidationException("Role already exists."));
                    return Mono.just(currentRole);
                });
    }
}
