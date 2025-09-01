package co.pragma.api.mapper.user.support;

import co.pragma.api.dto.user.ResponseUserDto;
import co.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDomainToResponse {

    @UserInMapToResponse
    ResponseUserDto toResponseDto(User user);
}
