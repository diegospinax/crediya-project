package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

public class UserDateBirthTest {

    @Test
    public void shouldThrowWhenDateBirthNullProvided() {
        Mono<UserDateBirth> dateBirthMono = UserDateBirth.create(null);

        StepVerifier.create(dateBirthMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Date of birth is required."))
                .verify();
    }

    @Test
    public void shouldThrowWhenDateBirthInvalidProvided() {
        Mono<UserDateBirth> dateBirthMono = UserDateBirth.create(LocalDate.of(2026, 8, 23));

        StepVerifier.create(dateBirthMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Invalid date of birth."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserDateBirth> dateBirthMono = UserDateBirth.create(LocalDate.of(2004, 8, 23));

        StepVerifier.create(dateBirthMono)
                .expectNext(dateBirthMono.block())
                .verifyComplete();
    }
}
