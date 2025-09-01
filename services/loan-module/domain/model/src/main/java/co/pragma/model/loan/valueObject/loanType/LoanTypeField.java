package co.pragma.model.loan.valueObject.loanType;

import reactor.core.publisher.Mono;

public abstract class LoanTypeField<T> {
    public T value;

    public LoanTypeField(T value) {
        this.value = value;
    }

    public Mono<Void> validate() {
        return Mono.empty();
    }
}
