package co.pragma.r2dbc;

import co.pragma.model.role.Role;
import co.pragma.model.role.valueObject.RoleDescription;
import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.role.valueObject.RoleName;
import co.pragma.r2dbc.role.RoleEntity;
import co.pragma.r2dbc.role.RoleR2DBCRepository;
import co.pragma.r2dbc.role.RoleRepositoryAdapter;
import co.pragma.r2dbc.role.mapper.RoleAdapterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryAdapterTest {

    private Role role;
    private RoleEntity roleEntity;

    @Mock
    RoleR2DBCRepository repository;

    @Mock
    RoleAdapterMapper roleAdapterMapper;

    @InjectMocks
    RoleRepositoryAdapter repositoryAdapter;

    @BeforeEach()
    public void setUp() {
        role = new Role(
                RoleId.create(1L).block(),
                RoleName.create("CONSULTANT").block(),
                RoleDescription.create("Consultant role.").block()
        );

        roleEntity = new RoleEntity();
        roleEntity.setId(1L);
        roleEntity.setName("CONSULTANT");
        roleEntity.setDescription("Consultant role.");
    }


    @Test
    void mustFindValueById() {
        when(repository.findById(any(Long.class))).thenReturn(Mono.just(roleEntity));
        when(roleAdapterMapper.mapToDomain(any(RoleEntity.class))).thenReturn(Mono.just(role));

        StepVerifier.create(repositoryAdapter.findById(role.id()))
                .expectNextMatches(roleById -> roleById.name().value.equals(role.name().value))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(roleEntity));
        when(roleAdapterMapper.mapToDomain(any(RoleEntity.class))).thenReturn(Mono.just(role));

        StepVerifier.create(repositoryAdapter.findAll())
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void mustFindByName() {
        when(repository.findByName(any(String.class))).thenReturn(Mono.just(roleEntity));
        when(roleAdapterMapper.mapToDomain(any(RoleEntity.class))).thenReturn(Mono.just(role));

        StepVerifier.create(repositoryAdapter.findByName(role.name()))
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void mustUpdateUser() {
        when(repository.save(any(RoleEntity.class))).thenReturn(Mono.just(roleEntity));
        when(roleAdapterMapper.mapToDomain(any(RoleEntity.class))).thenReturn(Mono.just(role));
        when(roleAdapterMapper.mapToEntity(any(Role.class))).thenReturn(roleEntity);

        StepVerifier.create(repositoryAdapter.updateRole(role))
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void mustSaveUser() {
        when(repository.save(any(RoleEntity.class))).thenReturn(Mono.just(roleEntity));
        when(roleAdapterMapper.mapToDomain(any(RoleEntity.class))).thenReturn(Mono.just(role));
        when(roleAdapterMapper.mapToEntity(any(Role.class))).thenReturn(roleEntity);

        StepVerifier.create(repositoryAdapter.createRole(role))
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void mustDeleteUser() {
        when(repository.deleteById(any(Long.class))).thenReturn(Mono.empty());

        StepVerifier.create(repositoryAdapter.deleteRole(role.id()))
                .expectNext()
                .verifyComplete();
    }

}
