package co.pragma.usecase.user;

import co.pragma.model.role.Role;
import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.*;
import co.pragma.usecase.user.support.UserUpdateHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {

//    private User user;
//    private Role role;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @InjectMocks
//    private UserUseCase userUseCase;
//
//    @BeforeEach()
//    public void setUp() {
//        user = new User(
//                UserId.create(1L).block(),
//                UserName.create("Danna").block(),
//                UserLastname.create("García").block(),
//                UserDateBirth.create(LocalDate.of(1992, 8, 12)).block(),
//                UserAddress.create("Calle_25_#54-30._Bogotá,_Colombia").block(),
//                UserPhoneNumber.create("3104959023").block(),
//                UserEmail.create("d@mail.com").block(),
//                UserSalary.create(1000000d).block(),
//                RoleId.create(1L).block()
//        );
//        role = new Role(
//                RoleId.create(1L).block(),
//                RoleName.create("CONSULTANT").block(),
//                RoleDescription.create("Consultant role.").block()
//        );
//    }
//
//    @Test
//    @DisplayName("Should create user.")
//    public void createUserUseCaseShouldCreate () {
//        Mockito.when(userRepository.findByEmail(any(UserEmail.class))).thenReturn(Mono.empty());
//        Mockito.when(roleRepository.findById(any(RoleId.class))).thenReturn(Mono.just(role));
//        Mockito.when(userRepository.createUser(any(User.class))).thenReturn(Mono.just(user));
//
//        StepVerifier.create(userUseCase.createUser(user))
//                .expectNext(user)
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findByEmail(any(UserEmail.class));
//        Mockito.verify(roleRepository).findById(any(RoleId.class));
//        Mockito.verify(userRepository).createUser(any(User.class));
//    }
//
//
//    @Test
//    @DisplayName("Should find user by id.")
//    public void shouldFindUserById() {
//        Mockito.when(userRepository.findById(any(UserId.class))).thenReturn(Mono.just(user));
//
//        StepVerifier.create(userUseCase.findById(user.id()))
//                .expectNext(user)
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findById(any(Long.class));
//    }
//
//    @Test
//    @DisplayName("Should find user by email.")
//    public void shouldFindUserByEmail() {
//        Mockito.when(userRepository.findByEmail(any(UserEmail.class))).thenReturn(Mono.just(user));
//
//        StepVerifier.create(userUseCase.findByEmail(user.email()))
//                .expectNext(user)
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findByEmail(any(UserEmail.class));
//    }
//
//    @Test
//    @DisplayName("Should find all users.")
//    public void shouldFindUsers() {
//        Mockito.when(userRepository.findAll()).thenReturn(Flux.just(user));
//
//        StepVerifier.create(userUseCase.findAll())
//                .expectNext(user)
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findAll();
//    }
//
//    @Test
//    @DisplayName("Should update user.")
//    public void shouldUpdateUser() {
//        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Mono.just(user));
//        Mockito.when(roleRepository.findById(any(RoleId.class))).thenReturn(Mono.just(role));
//        Mockito.when(userRepository.updateUser(any(User.class))).thenReturn(Mono.just(user));
//
//        StepVerifier.create(userUseCase.updateUser(user.id(), user))
//                .expectNext(user)
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findById(any(Long.class));
//        Mockito.verify(roleRepository).findById(any(RoleId.class));
//        Mockito.verify(userRepository).updateUser(any(User.class));
//    }
//
//    @Test
//    @DisplayName("Should delete user.")
//    public void shouldDeleteUser() {
//        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Mono.just(user));
//        Mockito.when(userRepository.deleteUser(any(Long.class))).thenReturn(Mono.empty());
//
//        StepVerifier.create(userUseCase.deleteUser(user.id()))
//                .expectNext()
//                .verifyComplete();
//
//        Mockito.verify(userRepository).findById(any(Long.class));
//        Mockito.verify(userRepository).deleteUser(any(Long.class));
//    }

}
