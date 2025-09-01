package co.pragma.api.mapper.user.support;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "id.value")
@Mapping(target = "name", source = "name.value")
@Mapping(target = "lastname", source = "lastname.value")
@Mapping(target = "dateBirth", source = "dateBirth.value")
@Mapping(target = "address", source = "address.value")
@Mapping(target = "phoneNumber", source = "phoneNumber.value")
@Mapping(target = "email", source = "email.value")
@Mapping(target = "salary", source = "salary.value")
@Mapping(target = "roleId", source = "roleId.value")
public @interface UserInMapToResponse {

}
