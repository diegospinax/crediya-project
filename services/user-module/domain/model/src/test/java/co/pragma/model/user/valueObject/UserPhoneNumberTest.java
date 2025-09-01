package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserPhoneNumberTest {

    @Test
    public void shouldThrowWhenPhoneNullProvided() {
        Mono<UserPhoneNumber> numberMono = UserPhoneNumber.create(null);

        StepVerifier.create(numberMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Phone number is required."))
                .verify();
    }

    @Test
    public void shouldThrowWhenPhoneInvalidProvided() {
        Mono<UserPhoneNumber> numberMono = UserPhoneNumber.create("12345");

        StepVerifier.create(numberMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Invalid phone provided."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserPhoneNumber> numberMono = UserPhoneNumber.create("3103920000");

        StepVerifier.create(numberMono)
                .expectNext(numberMono.block())
                .verifyComplete();
    }
}
