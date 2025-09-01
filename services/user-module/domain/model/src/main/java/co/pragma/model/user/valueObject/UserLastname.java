package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserLastname extends UserField<String>{

    private UserLastname(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+(?:_[A-Za-zÁÉÍÓÚÑáéíóúñ]+)?$";
        if(value == null)
            return Mono.error(new UserValidationException("Lastname is required."));
        if(!value.matches(regex))
            return Mono.error(new UserValidationException("Lastnames must contain only letters and underscore between them."));

        this.value = this.value.toUpperCase();

        return Mono.empty();
    }

    public static Mono<UserLastname> create(String value) {
        UserLastname lastname = new UserLastname(value);
        return lastname.validate()
                .thenReturn(lastname);
    }
}
