package co.pragma.model.loan.valueObject.loan;

import co.pragma.model.loan.exception.LoanValidationException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class LoanTerm extends LoanField<LocalDate> {
    private LoanTerm(LocalDate value) {
        super(value);
    }

    @Override
    protected Mono<Void> validate() {
        LocalDate now = LocalDate.now();
        if (value == null) {
            return Mono.error(new LoanValidationException("Term is required."));
        }
        if (value.isBefore(now) || value.isEqual(now)) {
            return Mono.error(new LoanValidationException("Invalid term provided."));
        }

        return Mono.empty();
    }

    public static Mono<LoanTerm> create(LocalDate value) {
        LoanTerm term = new LoanTerm(value);
        return term.validate()
                .thenReturn(term);
    }
}
