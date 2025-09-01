package co.pragma.model.user;


import co.pragma.model.role.valueObject.RoleId;
import co.pragma.model.user.valueObject.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserCreationTest {

    @Test
    public void creationValidation() {

        Mono<User> userMono = Mono.zip(
                Arrays.asList(
                        UserId.create(1L),
                        UserName.create("Danna"),
                        UserLastname.create("García"),
                        UserDateBirth.create(LocalDate.of(2003, 12, 8)),
                        UserAddress.create("Calle_12_#54-12._Bogotá,_Colombia"),
                        UserPhoneNumber.create("3103920186"),
                        UserEmail.create("d@mail.com"),
                        UserSalary.create(1_000_000d),
                        RoleId.create(1L)
                ), fields -> {
                    return new User(
                            (UserId) fields[0],
                            (UserName) fields[1],
                            (UserLastname) fields[2],
                            (UserDateBirth) fields[3],
                            (UserAddress) fields[4],
                            (UserPhoneNumber) fields[5],
                            (UserEmail) fields[6],
                            (UserSalary) fields[7],
                            (RoleId) fields[8]);
                });

        StepVerifier.create(userMono)
                .expectNext(userMono.block())
                .verifyComplete();
    }
}
