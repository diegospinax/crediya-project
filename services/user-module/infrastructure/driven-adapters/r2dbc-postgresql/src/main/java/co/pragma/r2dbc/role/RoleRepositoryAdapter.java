package co.pragma.r2dbc.role;

import co.pragma.model.role.Role;
import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.r2dbc.role.mapper.RoleAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleR2DBCRepository repository;
    private final RoleAdapterMapper roleAdapterMapper;

    @Override
    @Transactional
    public Mono<Role> createRole(Role role) {
        RoleEntity roleEntity = roleAdapterMapper.mapToEntity(role);
        return repository.save(roleEntity)
                .flatMap(roleAdapterMapper::mapToDomain);
    }

    @Override
    public Flux<Role> findAll() {
        return repository.findAll()
                .flatMap(roleAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<Role> findById(RoleId roleId) {
        return repository.findById(roleId.value)
                .flatMap(roleAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<Role> findByName(RoleName name) {
        return repository.findByName(name.value)
                .flatMap(roleAdapterMapper::mapToDomain);
    }

    @Override
    @Transactional
    public Mono<Role> updateRole(Role role) {
        RoleEntity roleEntity = roleAdapterMapper.mapToEntity(role);
        return repository.save(roleEntity)
                .flatMap(roleAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<Void> deleteRole(RoleId roleId) {
        return repository.deleteById(roleId.value);
    }
}
