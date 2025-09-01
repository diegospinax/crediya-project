package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeId extends LoanTypeField<Long> {
    public LoanTypeId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Loan type id is required"));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeId> create(Long value) {
        LoanTypeId typeId = new LoanTypeId(value);
        return typeId.validate()
                .thenReturn(typeId);
    }
}
