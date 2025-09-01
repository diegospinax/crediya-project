package co.pragma.model.loan.valueObject.loan;

import co.pragma.model.loan.exception.LoanValidationException;
import reactor.core.publisher.Mono;

public class LoanAmount extends LoanField<Integer> {
    private LoanAmount(Integer value) {
        super(value);
    }

    @Override
    protected Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanValidationException("Amount is required."));
        }
        if (value <= 0) {
            return Mono.error(new LoanValidationException("Amount can not be zero or less."));
        }
        return Mono.empty();
    }

    public static Mono<LoanAmount> create(Integer value) {
        LoanAmount amount = new LoanAmount(value);
        return amount.validate()
                .thenReturn(amount);
    }
}
