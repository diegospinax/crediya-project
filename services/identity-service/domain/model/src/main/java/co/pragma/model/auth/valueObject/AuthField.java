package co.pragma.model.auth.valueObject;

import reactor.core.publisher.Mono;

public abstract class AuthField<T> {
    public T value;

    public AuthField(T value) {
        this.value = value;
    }

    public Mono<Void> validate() {
        return Mono.empty();
    }
}
