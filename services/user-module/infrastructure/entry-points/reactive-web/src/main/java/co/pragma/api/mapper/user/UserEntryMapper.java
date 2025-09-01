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

    public ResponseUserDto mapToResponse (User user) {
        return mapperToResponse.toResponseDto(user);
    }

    public Mono<User> mapToDomain(SaveUserDto saveUserDto) {
        List<Mono<?>> fieldsList = Arrays.asList(
                UserName.create(saveUserDto.name()),
                UserLastname.create(saveUserDto.lastname()),
                UserDateBirth.create(saveUserDto.dateBirth()),
                UserAddress.create(saveUserDto.address()),
                UserPhoneNumber.create(saveUserDto.phoneNumber()),
                UserEmail.create(saveUserDto.email()),
                UserSalary.create(saveUserDto.salary()),
                RoleId.create(saveUserDto.roleId())
        );

        return Mono
                .zip(fieldsList, fields -> {
                    UserName name = (UserName) fields[0];
                    UserLastname lastname = (UserLastname) fields[1];
                    UserDateBirth dateBirth = (UserDateBirth) fields[2];
                    UserAddress address = (UserAddress) fields[3];
                    UserPhoneNumber phoneNumber = (UserPhoneNumber) fields[4];
                    UserEmail email = (UserEmail) fields[5];
                    UserSalary salary = (UserSalary) fields[6];
                    RoleId roleId = (RoleId) fields[7];

                    return new User(null, name, lastname, dateBirth, address, phoneNumber, email, salary, roleId);
                });
    }
}
