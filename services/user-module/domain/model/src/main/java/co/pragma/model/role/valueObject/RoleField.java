package co.pragma.model.role.valueObject;

import reactor.core.publisher.Mono;

abstract class RoleField<T> {
    public T value;

    public RoleField(T value) {
        this.value = value;
    }

    public Mono<Void> validate() {
        return Mono.empty();
    }
}
