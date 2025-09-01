package co.pragma.model.user.valueObject;

import reactor.core.publisher.Mono;

public abstract class UserField<T> {
    public T value;

    public UserField(T value) {
        this.value = value;
    }

    public Mono<Void> validate() {
        return Mono.empty();
    }
}
