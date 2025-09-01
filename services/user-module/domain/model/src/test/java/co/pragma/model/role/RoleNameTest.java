package co.pragma.model.role;

import co.pragma.model.role.exception.RoleValidationException;
import co.pragma.model.role.valueObject.RoleName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RoleNameTest {

    @Test
    public void shouldThrowWhenRoleNameNullProvided() {
        Mono<RoleName> nameMono = RoleName.create(null);

        StepVerifier.create(nameMono)
                .expectErrorMatches(throwable -> throwable instanceof RoleValidationException && throwable
                        .getMessage()
                        .equals("Role name is required."))
                .verify();
    }

    @Test
    public void invalidRoleDescriptionProvidedTest() {
        Mono<RoleName> nameMono = RoleName.create(" ");

        StepVerifier.create(nameMono)
                .expectErrorMatches(throwable -> throwable instanceof RoleValidationException && throwable
                        .getMessage()
                        .equals("Role must contain only letters and underscore."))
                .verify();
    }

    @Test
    public void validRoleDescriptionTest() {
        Mono<RoleName> nameMono = RoleName.create("EMPLOYEE_ROLE");

        StepVerifier.create(nameMono)
                .expectNext(nameMono.block())
                .verifyComplete();
    }
}
