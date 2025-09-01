package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserId extends UserField<Long>{

    private UserId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null){
             return Mono.error(new UserValidationException("User id is required."));
        }
        return Mono.empty();
    }

    public static Mono<UserId> create (Long value) {
        UserId userId = new UserId(value);
        return userId.validate()
                .thenReturn(userId);
    }
}
