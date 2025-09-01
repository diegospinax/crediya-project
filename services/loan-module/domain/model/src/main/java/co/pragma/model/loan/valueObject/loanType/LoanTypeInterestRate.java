package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeInterestRate extends LoanTypeField<Float> {
    public LoanTypeInterestRate(Float value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Interest rate is required"));
        }
        if (value < 0 || value > 1) {
            return Mono.error(new LoanTypeValidationException("Interest rate must be between or equals 0 and 1."));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeInterestRate> create(Float value) {
        LoanTypeInterestRate interestRate = new LoanTypeInterestRate(value);
        return interestRate.validate()
                .thenReturn(interestRate);
    }
}
