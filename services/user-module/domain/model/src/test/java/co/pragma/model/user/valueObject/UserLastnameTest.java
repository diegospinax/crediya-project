package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserLastnameTest {

    @Test
    public void shouldThrowWhenNameNullProvided() {
        Mono<UserLastname> lastnameMono = UserLastname.create(null);

        StepVerifier.create(lastnameMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Lastname is required."))
                .verify();
    }

    @Test
    public void shouldThrowWhenNameInvalidProvided() {
        Mono<UserLastname> lastnameMono = UserLastname.create("Giraldo123");

        StepVerifier.create(lastnameMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Lastnames must contain only letters and underscore between them."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserLastname> lastnameMono = UserLastname.create("Giraldo_Gonzáles");

        StepVerifier.create(lastnameMono)
                .expectNext(lastnameMono.block())
                .verifyComplete();
    }

}
