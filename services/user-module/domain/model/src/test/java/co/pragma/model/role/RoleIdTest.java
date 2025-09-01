package co.pragma.model.role;

import co.pragma.model.role.exception.RoleValidationException;
import co.pragma.model.role.valueObject.RoleId;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class RoleIdTest {

    @Test
    public void shouldThrowWhenRoleIdNullProvided() {
        Mono<RoleId> idMono = RoleId.create(null);

        StepVerifier.create(idMono)
                .expectErrorMatches(throwable -> throwable instanceof RoleValidationException && throwable
                        .getMessage()
                        .equals("Role Id is required."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<RoleId> idMono = RoleId.create(1L);

        StepVerifier.create(idMono)
                .expectNext(idMono.block())
                .verifyComplete();
    }

}
