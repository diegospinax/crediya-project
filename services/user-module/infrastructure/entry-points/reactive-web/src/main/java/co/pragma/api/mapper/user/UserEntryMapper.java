package co.pragma.api.mapper.user;

import co.pragma.api.dto.user.ResponseUserDto;
import co.pragma.api.dto.user.SaveUserDto;
import co.pragma.api.mapper.user.support.UserDomainToResponse;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.*;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class UserEntryMapper {

    private final UserDomainToResponse mapperToResponse;

    public UserEntryMapper(UserDomainToResponse mapperToResponse) {
        this.mapperToResponse = mapperToResponse;
    }

    public ResponseUserDto mapToResponse(User user) {
        return mapperToResponse.toResponseDto(user);
    }

    public Mono<User> mapToDomain(SaveUserDto saveUserDto) {
        List<Mono<?>> fieldsList = Arrays.asList(
                UserName.create(saveUserDto.name()),
                UserLastname.create(saveUserDto.lastname()),
                UserDocument.create(saveUserDto.document()),
                UserDateBirth.create(saveUserDto.dateBirth()),
                UserAddress.create(saveUserDto.address()),
                UserPhoneNumber.create(saveUserDto.phoneNumber()),
                UserEmail.create(saveUserDto.email()),
                UserSalary.create(saveUserDto.salary()),
                RoleId.create(saveUserDto.roleId())
        );

        return Mono
                .zip(fieldsList, fields -> new User(
                        null,
                        (UserName) fields[0],
                        (UserLastname) fields[1],
                        (UserDocument) fields[2],
                        (UserDateBirth) fields[3],
                        (UserAddress) fields[4],
                        (UserPhoneNumber) fields[5],
                        (UserEmail) fields[6],
                        (UserSalary) fields[7],
                        (RoleId) fields[8]
                ));
    }

    //TODO check for null values in PUT mapping
}
