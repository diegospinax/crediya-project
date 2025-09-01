package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserNameTest {

    @Test
    public void shouldThrowWhenNameNullProvided() {
        Mono<UserName> nameMono = UserName.create(null);

        StepVerifier.create(nameMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Name is required."))
                .verify();
    }

    @Test
    public void shouldThrowWhenNameInvalidProvided() {
        Mono<UserName> nameMono = UserName.create("Danna123");

        StepVerifier.create(nameMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Names must contain only letters and underscore between them."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserName> nameMono = UserName.create("juan_felipe");

        StepVerifier.create(nameMono)
                .expectNext(nameMono.block())
                .verifyComplete();
    }
}
