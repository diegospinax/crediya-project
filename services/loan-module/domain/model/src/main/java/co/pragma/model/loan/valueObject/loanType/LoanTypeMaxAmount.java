package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeMaxAmount extends LoanTypeField<Integer>{
    public LoanTypeMaxAmount(Integer value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Maximum amount is required."));
        }
        if (value <= 0) {
            return Mono.error(new LoanTypeValidationException("Maximum amount can not be zero or less."));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeMaxAmount> create(Integer value) {
        LoanTypeMaxAmount maxAmount = new LoanTypeMaxAmount(value);
        return maxAmount.validate()
                .thenReturn(maxAmount);
    }
}
