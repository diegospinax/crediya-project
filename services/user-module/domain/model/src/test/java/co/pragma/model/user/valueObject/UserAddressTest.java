package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserAddressTest {

    @Test
    public void shouldThrowWhenAddressNullProvided() {
        Mono<UserAddress> addressMono = UserAddress.create(null);

        StepVerifier.create(addressMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Address is required."))
                .verify();
    }

    @Test
    public void shouldCreate() {
        Mono<UserAddress> addressMono = UserAddress.create("Calle_16_#75-10._Manizales,_Colombia");

        StepVerifier.create(addressMono)
                .expectNext(addressMono.block())
                .verifyComplete();
    }

    @Test
    public void shouldThrowWhenAddressInvalidProvided() {
        Mono<UserAddress> addressMono = UserAddress.create("Calle_27_#30-02._Bogotá121,_Colombia");

        StepVerifier.create(addressMono)
                .expectErrorMatches(throwable -> throwable instanceof UserValidationException && throwable
                        .getMessage()
                        .equals("Invalid address provided."))
                .verify();
    }

}
