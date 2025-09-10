package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class Token {
    public String value;

    private Token(String value) {
        this.value = value;
    }

    private Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new AuthValidationException("Token can't be null."));
        }
        return Mono.empty();
    }

    public static Mono<Token> create(String value) {
        Token token = new Token(value);
        return token.validate()
                .thenReturn(token);
    }
}
