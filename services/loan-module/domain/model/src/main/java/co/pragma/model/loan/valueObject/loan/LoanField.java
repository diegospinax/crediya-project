package co.pragma.model.loan.valueObject.loan;

import reactor.core.publisher.Mono;

public abstract class LoanField<T> {

    public T value;

    public LoanField (T value) {
        this.value = value;
    }

    protected Mono<Void> validate() {
        return Mono.empty();
    }

}
