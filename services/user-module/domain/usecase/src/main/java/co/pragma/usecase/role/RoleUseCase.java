package co.pragma.usecase.role;

import co.pragma.model.role.Role;
import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import co.pragma.usecase.role.cases.CreateRoleUseCase;
import co.pragma.usecase.role.cases.DeleteRoleUseCase;
import co.pragma.usecase.role.cases.FindRoleUseCase;
import co.pragma.usecase.role.cases.UpdateRoleUseCase;
import co.pragma.usecase.role.support.RoleUpdateHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RoleUseCase implements CreateRoleUseCase, FindRoleUseCase, UpdateRoleUseCase, DeleteRoleUseCase {

    private final RoleRepository roleRepository;

    public RoleUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<Role> createRole(Role role) {
        return roleRepository.findByName(role.name())
                .hasElement()
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new DataIntegrationValidationException("Role already exists."));
                    }
                    return roleRepository.createRole(role);
                });
    }

    @Override
    public Mono<Void> deleteRole(RoleId roleId) {
        return roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role not found.")))
                .flatMap(role -> roleRepository.deleteRole(role.id()));
    }

    @Override
    public Flux<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Mono<Role> findById(RoleId roleId) {
        return roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role not found.")));
    }

    @Override
    public Mono<Role> findByName(RoleName name) {
        return roleRepository.findByName(name)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role not found.")));
    }

    @Override
    public Mono<Role> updateRole(RoleId roleId, Role roleNewData) {
        RoleUpdateHelper roleUpdateHelper = new RoleUpdateHelper(roleRepository);
        return roleRepository.findById(roleId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role not found.")))
                .flatMap(role -> roleUpdateHelper.validateNameChange(roleNewData, role))
                .map(role -> roleUpdateHelper.updateFields(roleNewData, role))
                .flatMap(roleRepository::updateRole);
    }

}
