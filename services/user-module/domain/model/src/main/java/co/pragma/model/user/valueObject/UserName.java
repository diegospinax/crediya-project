package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserName extends UserField<String> {

    private UserName(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+(?:_[A-Za-zÁÉÍÓÚÑáéíóúñ]+)?$";
        if(value == null)
            return Mono.error(new UserValidationException("Name is required."));
        if(!value.matches(regex))
            return Mono.error(new UserValidationException("Names must contain only letters and underscore between them."));

        this.value = this.value.toUpperCase();

        return Mono.empty();
    }

    public static Mono<UserName> create(String value) {
        UserName name = new UserName(value);
        return name.validate()
                .thenReturn(name);
    }
}
