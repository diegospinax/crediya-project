package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserEmailTest {

    @Test
    public void shouldThrowWhenEmailNullProvided() {
        Mono<UserEmail> emailMono = UserEmail.create(null);

        StepVerifier.create(emailMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Email is required."))
                .verify();
    }

    @Test
    public void invalidEmail() {
        Mono<UserEmail> emailMono = UserEmail.create("aeiou@.com");

        StepVerifier.create(emailMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Invalid email provided."))
                .verify();
    }

    @Test
    public void validEmail() {
        Mono<UserEmail> emailMono = UserEmail.create("d@mail.com");

        StepVerifier.create(emailMono)
                .expectNext(emailMono.block())
                .verifyComplete();
    }
}
