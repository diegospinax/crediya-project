package co.pragma.model.loan.valueObject.loan;

import co.pragma.model.loan.exception.LoanValidationException;
import reactor.core.publisher.Mono;

public class LoanUserDocument extends LoanField<String> {
    private LoanUserDocument(String value) {
        super(value);
    }

    @Override
    protected Mono<Void> validate() {
        String regex = "^\\d{10}$";
        if (value == null) {
            return Mono.error(new LoanValidationException("User document is required."));
        }
        if (!value.matches(regex)) {
            return Mono.error(new LoanValidationException("Invalid user document provided."));
        }
        return Mono.empty();
    }

    public static Mono<LoanUserDocument> create(String value) {
        LoanUserDocument userDocument = new LoanUserDocument(value);
        return userDocument.validate()
                .thenReturn(userDocument);
    }
}
