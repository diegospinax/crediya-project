package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserPhoneNumber extends UserField<String> {

    private UserPhoneNumber(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^\\d{10}$";
        if (value == null)
            return Mono.error(new UserValidationException("Phone number is required."));
        if (!value.matches(regex))
            return Mono.error(new UserValidationException("Invalid phone provided."));

        return Mono.empty();
    }

    public static Mono<UserPhoneNumber> create(String value) {
        UserPhoneNumber phoneNumber = new UserPhoneNumber(value);
        return phoneNumber.validate()
                .thenReturn(phoneNumber);
    }
}
