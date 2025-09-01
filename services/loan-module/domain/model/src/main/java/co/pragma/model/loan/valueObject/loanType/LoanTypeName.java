package co.pragma.model.loan.valueObject.loanType;

import co.pragma.model.loan.exception.LoanTypeValidationException;
import reactor.core.publisher.Mono;

public class LoanTypeName extends LoanTypeField<String> {
    public LoanTypeName(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^\\p{L}+(?:_\\p{L}+)*$";
        if (value == null) {
            return Mono.error(new LoanTypeValidationException("Loan type name is required."));
        }
        if (value.length() < 3) {
            return Mono.error(new LoanTypeValidationException("Loan type name min length are 3 characters."));
        }
        if (!value.matches(regex)){
            return Mono.error(new LoanTypeValidationException("Loan type name must contain only letters an underscore."));
        }
        return Mono.empty();
    }

    public static Mono<LoanTypeName> create(String value) {
        LoanTypeName typeName = new LoanTypeName(value);
        return typeName.validate()
                .thenReturn(typeName);
    }
}
