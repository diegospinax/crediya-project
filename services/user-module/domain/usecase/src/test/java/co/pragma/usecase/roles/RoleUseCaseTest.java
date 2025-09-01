package co.pragma.usecase.roles;

import co.pragma.model.role.Role;
import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.usecase.role.RoleUseCase;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RoleUseCaseTest {

    private Role role;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleUseCase roleUseCase;

    @BeforeEach()
    public void setUp() {
        role = new Role(
                RoleId.create(1L).block(),
                RoleName.create("CONSULTANT").block(),
                RoleDescription.create("Consultant role.").block()
        );
    }

    @Test
    @DisplayName("Should create role.")
    public void shouldCreateRole() {
        Mockito.when(roleRepository.findByName(any(RoleName.class))).thenReturn(Mono.empty());
        Mockito.when(roleRepository.createRole(any(Role.class))).thenReturn(Mono.just(role));

        StepVerifier.create(roleUseCase.createRole(role))
                .expectNext(role)
                .verifyComplete();

        Mockito.verify(roleRepository).findByName(any(RoleName.class));
        Mockito.verify(roleRepository).createRole(any(Role.class));
    }

    @Test
    @DisplayName("Should find role by id.")
    public void shouldFindRoleById() {
        Mockito.when(roleRepository.findById(any(RoleId.class))).thenReturn(Mono.just(role));

        StepVerifier.create(roleUseCase.findById(role.id()))
                .expectNext(role)
                .verifyComplete();

        Mockito.verify(roleRepository).findById(any(RoleId.class));
    }

    @Test
    @DisplayName("Should find role by name.")
    public void shouldFindRoleByName() {
        Mockito.when(roleRepository.findByName(any(RoleName.class))).thenReturn(Mono.just(role));

        StepVerifier.create(roleUseCase.findByName(role.name()))
                .expectNext(role)
                .verifyComplete();

        Mockito.verify(roleRepository).findByName(any(RoleName.class));
    }

    @Test
    @DisplayName("Should find all roles.")
    public void shouldFindRoles() {
        Mockito.when(roleRepository.findAll()).thenReturn(Flux.just(role));

        StepVerifier.create(roleUseCase.findAll())
                .expectNext(role)
                .verifyComplete();

        Mockito.verify(roleRepository).findAll();
    }

    @Test
    @DisplayName("Should update role.")
    public void shouldUpdateRole() {
        Mockito.when(roleRepository.findById(any(RoleId.class))).thenReturn(Mono.just(role));
        Mockito.when(roleRepository.updateRole(any(Role.class))).thenReturn(Mono.just(role));

        StepVerifier.create(roleUseCase.updateRole(role.id(), role))
                .assertNext(newRole -> Assertions.assertEquals("CONSULTANT", newRole.name().value))
                .verifyComplete();

        Mockito.verify(roleRepository).findById(any(RoleId.class));
        Mockito.verify(roleRepository).updateRole(any(Role.class));
    }

    @Test
    @DisplayName("Should delete user.")
    public void shouldDeleteUser() {
        Mockito.when(roleRepository.findById(any(RoleId.class))).thenReturn(Mono.just(role));
        Mockito.when(roleRepository.deleteRole(any(RoleId.class))).thenReturn(Mono.empty());

        StepVerifier.create(roleUseCase.deleteRole(role.id()))
                .expectNext()
                .verifyComplete();

        Mockito.verify(roleRepository).findById(any(RoleId.class));
        Mockito.verify(roleRepository).deleteRole(any(RoleId.class));
    }
}
