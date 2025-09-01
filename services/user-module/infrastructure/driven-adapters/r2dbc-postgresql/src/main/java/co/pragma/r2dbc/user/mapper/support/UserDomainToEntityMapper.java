package co.pragma.r2dbc.user.mapper.support;

import co.pragma.model.user.User;
import co.pragma.r2dbc.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDomainToEntityMapper {

    @UserMapDomainToEntity
    UserEntity domainToEntity(User user);

}
