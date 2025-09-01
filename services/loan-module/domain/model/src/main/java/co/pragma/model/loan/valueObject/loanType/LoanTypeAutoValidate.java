package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeAutoValidate extends LoanTypeField<Boolean> {

    public LoanTypeAutoValidate(Boolean value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Auto validate is required"));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeAutoValidate> create(Boolean value) {
        LoanTypeAutoValidate autoValidate = new LoanTypeAutoValidate(value);
        return autoValidate.validate()
                .thenReturn(autoValidate);
    }
}
