package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserDocument extends UserField<String>{
    private UserDocument(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^\\d{10}$";
        if (value == null) {
            return Mono.error(new UserValidationException("Document is required."));
        }
        if (!value.matches(regex)) {
            return Mono.error(new UserValidationException("Invalid document provided."));
        }
        return Mono.empty();
    }

    public static Mono<UserDocument> create(String value) {
        UserDocument userDocument = new UserDocument(value);
        return userDocument.validate()
                .thenReturn(userDocument);
    }
}
