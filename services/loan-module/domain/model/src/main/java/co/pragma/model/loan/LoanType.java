package co.pragma.model.loan;

import co.pragma.model.loan.valueObject.loanType.*;

public record LoanType(
        LoanTypeId id,
        LoanTypeName name,
        LoanTypeMinAmount minAmount,
        LoanTypeMaxAmount maxAmount,
        LoanTypeInterestRate interestRate,
        LoanTypeAutoValidate autoValidate
) {
}
