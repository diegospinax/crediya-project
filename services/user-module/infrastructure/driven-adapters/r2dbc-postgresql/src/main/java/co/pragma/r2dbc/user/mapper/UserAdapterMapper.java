package co.pragma.r2dbc.user.mapper;

import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.*;
import co.pragma.r2dbc.user.UserEntity;
import co.pragma.r2dbc.user.mapper.support.UserDomainToEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAdapterMapper {

    private final UserDomainToEntityMapper mapperToEntity;

    public UserEntity mapToEntity(User user) {
        return mapperToEntity.domainToEntity(user);
    }

    public Mono<User> mapToDomain(UserEntity entity) {
        List<Mono<?>> fieldsList = Arrays.asList(
                UserId.create(entity.getId()),
                UserName.create(entity.getName()),
                UserLastname.create(entity.getLastname()),
                UserDocument.create(entity.getDocument()),
                UserDateBirth.create(entity.getDateBirth()),
                UserAddress.create(entity.getAddress()),
                UserPhoneNumber.create(entity.getPhoneNumber()),
                UserEmail.create(entity.getEmail()),
                UserSalary.create(entity.getSalary()),
                RoleId.create(entity.getRoleId())
        );

        return Mono
                .zip(fieldsList, fields -> new User(
                        (UserId) fields[0],
                        (UserName) fields[1],
                        (UserLastname) fields[2],
                        (UserDocument) fields[3],
                        (UserDateBirth) fields[4],
                        (UserAddress) fields[5],
                        (UserPhoneNumber) fields[6],
                        (UserEmail) fields[7],
                        (UserSalary) fields[8],
                        (RoleId) fields[9]
                ));
    }
}
