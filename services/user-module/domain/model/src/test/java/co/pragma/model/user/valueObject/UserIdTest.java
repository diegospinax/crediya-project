package co.pragma.model.user.valueObject;

import static org.junit.jupiter.api.Assertions.*;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserIdTest {

    @Test
    public void shouldThrowWhenIdNullProvided() {
        Mono<UserId> idMono = UserId.create(null);
        StepVerifier.create(idMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("User id is required."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserId> idMono = UserId.create(1L);

        StepVerifier.create(idMono)
                .expectNext(idMono.block())
                .verifyComplete();
    }
}
