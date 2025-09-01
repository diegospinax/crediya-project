package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserEmail extends UserField<String> {

    private UserEmail(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if(value == null){
            return Mono.error(new UserValidationException("Email is required."));
        }
        if(!value.matches(regex)){
            return Mono.error(new UserValidationException("Invalid email provided."));
        }

        return Mono.empty();
    }

    public static Mono<UserEmail> create(String value) {
        UserEmail email = new UserEmail(value);
        return email.validate()
                .thenReturn(email);
    }
}
