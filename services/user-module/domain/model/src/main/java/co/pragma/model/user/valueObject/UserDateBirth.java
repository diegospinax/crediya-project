package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class UserDateBirth extends UserField<LocalDate> {

    private UserDateBirth(LocalDate value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        LocalDate now = LocalDate.now();
        if(value == null)
            return Mono.error(new UserValidationException("Date of birth is required."));
        if (value.isEqual(now) || value.isAfter(now)) {
            return Mono.error(new UserValidationException("Invalid date of birth."));
        }
        return Mono.empty();
    }

    public static Mono<UserDateBirth> create(LocalDate value) {
        UserDateBirth dateBirth = new UserDateBirth(value);
        return dateBirth.validate()
                .thenReturn(dateBirth);
    }
}
