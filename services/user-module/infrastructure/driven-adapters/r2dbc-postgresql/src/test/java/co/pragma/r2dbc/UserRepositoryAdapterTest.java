package co.pragma.r2dbc;

import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.*;
import co.pragma.r2dbc.user.UserEntity;
import co.pragma.r2dbc.user.UserR2DBCRepository;
import co.pragma.r2dbc.user.UserRepositoryAdapter;
import co.pragma.r2dbc.user.mapper.UserAdapterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    private User user;
    private UserEntity userEntity;

    @Mock
    UserR2DBCRepository userRepository;

    @Mock
    UserAdapterMapper userAdapterMapper;

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @BeforeEach()
    public void setUp() {
        user = new User(
                UserId.create(1L).block(),
                UserName.create("Danna").block(),
                UserLastname.create("García").block(),
                UserDocument.create("1032700000").block(),
                UserDateBirth.create(LocalDate.of(1992, 8, 12)).block(),
                UserAddress.create("Calle_25_#54-30._Bogotá,_Colombia").block(),
                UserPhoneNumber.create("3104959023").block(),
                UserEmail.create("d@mail.com").block(),
                UserSalary.create(1000000d).block(),
                RoleId.create(1L).block()
        );

        userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setName("Danna");
        userEntity.setLastname("García");
        userEntity.setDateBirth(LocalDate.of(1992, 8, 12));
        userEntity.setAddress("Calle_25_#54-30._Bogotá,_Colombia");
        userEntity.setPhoneNumber("3104959023");
        userEntity.setEmail("d@mail.com");
        userEntity.setSalary(1000000d);
        userEntity.setRoleId(1L);
    }

    @Test
    void mustFindValueById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Mono.just(userEntity));
        when(userAdapterMapper.mapToDomain(any(UserEntity.class))).thenReturn(Mono.just(user));

        StepVerifier.create(repositoryAdapter.findById(UserId.create(1L).block()))
                .expectNextMatches(userById -> userById.email().value.equals(user.email().value))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(userRepository.findAll()).thenReturn(Flux.just(userEntity));
        when(userAdapterMapper.mapToDomain(any(UserEntity.class))).thenReturn(Mono.just(user));

        StepVerifier.create(repositoryAdapter.findAll())
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void mustFindByEmail() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Mono.just(userEntity));
        when(userAdapterMapper.mapToDomain(any(UserEntity.class))).thenReturn(Mono.just(user));

        StepVerifier.create(repositoryAdapter.findByEmail(user.email()))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void mustUpdateUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(userAdapterMapper.mapToDomain(any(UserEntity.class))).thenReturn(Mono.just(user));
        when(userAdapterMapper.mapToEntity(any(User.class))).thenReturn(userEntity);

        StepVerifier.create(repositoryAdapter.updateUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void mustSaveUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
        when(userAdapterMapper.mapToDomain(any(UserEntity.class))).thenReturn(Mono.just(user));
        when(userAdapterMapper.mapToEntity(any(User.class))).thenReturn(userEntity);

        StepVerifier.create(repositoryAdapter.createUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void mustDeleteUser() {
        when(userRepository.deleteById(any(Long.class))).thenReturn(Mono.empty());

        StepVerifier.create(repositoryAdapter.deleteUser(user.id()))
                .expectNext()
                .verifyComplete();
    }
}
