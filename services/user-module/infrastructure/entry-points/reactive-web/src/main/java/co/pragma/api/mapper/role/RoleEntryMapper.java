package co.pragma.api.mapper.role;

import co.pragma.api.dto.role.ResponseRoleDto;
import co.pragma.api.dto.role.SaveRoleDto;
import co.pragma.api.mapper.role.support.RoleDomainToResponseMapper;
import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RoleEntryMapper {

    private final RoleDomainToResponseMapper mapperToResponse;

    public RoleEntryMapper(RoleDomainToResponseMapper mapperToResponse) {
        this.mapperToResponse = mapperToResponse;
    }

    public ResponseRoleDto mapToResponse(Role role) {
        return mapperToResponse.domainToResponse(role);
    }

    public Mono<Role> mapToDomain(SaveRoleDto saveRoleDto) {
        Mono<RoleName> nameMono = RoleName.create(saveRoleDto.name());
        Mono<RoleDescription> descriptionMono = RoleDescription.create(saveRoleDto.description());

        return Mono
                .zip(nameMono, descriptionMono)
                .flatMap(roleFields -> {
                    RoleName roleName = roleFields.getT1();
                    RoleDescription roleDescription = roleFields.getT2();

                    return Mono.just(new Role(null, roleName, roleDescription));
                });
    }


}
