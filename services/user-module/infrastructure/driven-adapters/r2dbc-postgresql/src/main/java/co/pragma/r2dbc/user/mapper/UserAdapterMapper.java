package co.pragma.r2dbc.user.mapper;

import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.*;
import co.pragma.r2dbc.user.UserEntity;
import co.pragma.r2dbc.user.mapper.support.UserDomainToEntityMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class UserAdapterMapper {

    private final UserDomainToEntityMapper mapperToEntity;

    public UserAdapterMapper(UserDomainToEntityMapper mapperToEntity) {
        this.mapperToEntity = mapperToEntity;
    }

    public UserEntity mapToEntity(User user) {
        return mapperToEntity.domainToEntity(user);
    }

    public Mono<User> mapToDomain(UserEntity entity) {
        List<Mono<?>> fieldsList = Arrays.asList(
                UserId.create(entity.getId()),
                UserName.create(entity.getName()),
                UserLastname.create(entity.getLastname()),
                UserDateBirth.create(entity.getDateBirth()),
                UserAddress.create(entity.getAddress()),
                UserPhoneNumber.create(entity.getPhoneNumber()),
                UserEmail.create(entity.getEmail()),
                UserSalary.create(entity.getSalary()),
                RoleId.create(entity.getRoleId())
        );

        return Mono
                .zip(fieldsList, fields -> {
                    UserId id = (UserId) fields[0];
                    UserName name = (UserName) fields[1];
                    UserLastname lastname = (UserLastname) fields[2];
                    UserDateBirth dateBirth = (UserDateBirth) fields[3];
                    UserAddress address = (UserAddress) fields[4];
                    UserPhoneNumber phoneNumber = (UserPhoneNumber) fields[5];
                    UserEmail email = (UserEmail) fields[6];
                    UserSalary salary = (UserSalary) fields[7];
                    RoleId roleId = (RoleId) fields[8];

                    return new User(id, name, lastname, dateBirth, address, phoneNumber, email, salary, roleId);
                });
    }
}
