package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserSalaryTest {

    @Test
    public void shouldThrowWhenSalaryNullProvided() {
        Mono<UserSalary> salaryMono = UserSalary.create(null);

        StepVerifier.create(salaryMono)
                        .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                                .getMessage()
                                .equals("Salary is required."))
                                .verify();
    }

    @Test
    public void shouldThrowWhenSalaryInvalidProvided() {
        Mono<UserSalary> salaryMono = UserSalary.create(15_000_000.1);

        StepVerifier.create(salaryMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Invalid salary provided."))
                .verify();
    }

    @Test
    public void shouldCreate () {
        Mono<UserSalary> salaryMono = UserSalary.create(15_000_000d);

        StepVerifier.create(salaryMono)
                .expectNext(salaryMono.block())
                .verifyComplete();
    }
}
