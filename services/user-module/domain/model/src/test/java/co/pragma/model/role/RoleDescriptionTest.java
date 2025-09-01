package co.pragma.model.role;

import co.pragma.model.role.exception.RoleValidationException;
import co.pragma.model.role.valueObject.RoleDescription;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RoleDescriptionTest {

    @Test
    public void shouldThrowWhenRoleDescriptionNullProvided() {
        Mono<RoleDescription> descriptionMono = RoleDescription.create(null);

        StepVerifier.create(descriptionMono)
                .expectErrorMatches(throwable -> throwable instanceof RoleValidationException && throwable
                        .getMessage()
                        .equals("Role description is required."))
                .verify();
    }

    @Test
    public void shouldThrowWhenRoleDescriptionInvalidProvided() {
        Mono<RoleDescription> descriptionMono = RoleDescription.create("");

        StepVerifier.create(descriptionMono)
                .expectErrorMatches(throwable -> throwable instanceof RoleValidationException && throwable
                        .getMessage()
                        .equals("Role description must only contain letters, numbers, comma and period."))
                .verify();
    }

    @Test
    public void validRoleDescriptionTest() {
        Mono<RoleDescription> descriptionMono = RoleDescription.create("Employee User Role, area 12.");

        StepVerifier.create(descriptionMono)
                .expectNext(descriptionMono.block())
                .verifyComplete();
    }
}
