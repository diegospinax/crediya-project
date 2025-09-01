package co.pragma.r2dbc.role.mapper.support;

import co.pragma.model.role.Role;
import co.pragma.r2dbc.role.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleDomainToEntityMapper {

    @RoleMapFromDomain
    RoleEntity domainToEntity(Role role);
}
