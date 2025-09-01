package co.pragma.model.loan.valueObject.loan;

import co.pragma.model.loan.exception.LoanValidationException;
import reactor.core.publisher.Mono;

public class LoanId extends LoanField<Long> {
    private LoanId(Long value) {
        super(value);
    }

    @Override
    protected Mono<Void> validate() {
        if(value == null) {
            return Mono.error(new LoanValidationException("Id is required."));
        }
        return Mono.empty();
    }

    public static Mono<LoanId> create(Long value) {
        LoanId loanId = new LoanId(value);
        return loanId.validate()
                .thenReturn(loanId);
    }
}
