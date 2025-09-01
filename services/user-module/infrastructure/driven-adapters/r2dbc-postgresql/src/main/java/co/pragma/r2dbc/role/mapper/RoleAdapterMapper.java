package co.pragma.r2dbc.role.mapper;

import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.r2dbc.role.RoleEntity;
import co.pragma.r2dbc.role.mapper.support.RoleDomainToEntityMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoleAdapterMapper {

    private final RoleDomainToEntityMapper mapperToEntity;

    public RoleAdapterMapper(RoleDomainToEntityMapper mapperToEntity) {
        this.mapperToEntity = mapperToEntity;
    }

    public RoleEntity mapToEntity(Role role) {
        return mapperToEntity.domainToEntity(role);
    }

    public Mono<Role> mapToDomain(RoleEntity entity) {
        Mono<RoleId> idMono = RoleId.create(entity.getId());
        Mono<RoleName> nameMono = RoleName.create(entity.getName());
        Mono<RoleDescription> descriptionMono = RoleDescription.create(entity.getDescription());

        return Mono
                .zip(idMono, nameMono, descriptionMono)
                .flatMap(roleFields -> {
                    RoleId roleId = roleFields.getT1();
                    RoleName roleName = roleFields.getT2();
                    RoleDescription roleDescription = roleFields.getT3();

                    return Mono.just(new Role(roleId, roleName, roleDescription));
                });
    }


}
