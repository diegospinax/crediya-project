package co.pragma.api.mapper.role.support;

import co.pragma.api.dto.role.ResponseRoleDto;
import co.pragma.model.role.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleDomainToResponseMapper {

    @RoleInMapFromDomain
    ResponseRoleDto domainToResponse(Role role);

}
