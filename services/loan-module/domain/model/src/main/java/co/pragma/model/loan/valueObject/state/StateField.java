package co.pragma.model.loan.valueObject.state;

import reactor.core.publisher.Mono;

public abstract class StateField<T> {
    public T value;

    public StateField(T value) {
        this.value = value;
    }

    public Mono<Void> validate() {
        return Mono.empty();
    }
}
