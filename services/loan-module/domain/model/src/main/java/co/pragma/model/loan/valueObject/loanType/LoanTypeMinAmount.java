package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeMinAmount extends LoanTypeField<Integer> {
    public LoanTypeMinAmount(Integer value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Minimum amount is required."));
        }
        if (value <= 0) {
            return Mono.error(new LoanTypeValidationException("Minimum amount can not be zero or less."));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeMinAmount> create(Integer value) {
        LoanTypeMinAmount minAmount = new LoanTypeMinAmount(value);
        return minAmount.validate()
                .thenReturn(minAmount);
    }
}
