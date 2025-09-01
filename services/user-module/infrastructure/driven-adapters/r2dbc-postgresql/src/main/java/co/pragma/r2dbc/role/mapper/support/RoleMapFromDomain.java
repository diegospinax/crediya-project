package co.pragma.r2dbc.role.mapper.support;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "id.value")
@Mapping(target = "name", source = "name.value")
@Mapping(target = "description", source = "description.value")
public @interface RoleMapFromDomain {
}
