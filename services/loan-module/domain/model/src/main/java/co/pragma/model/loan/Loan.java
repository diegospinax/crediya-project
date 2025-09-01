package co.pragma.model.loan;

import co.pragma.model.loan.valueObject.loan.LoanAmount;
import co.pragma.model.loan.valueObject.loan.LoanId;
import co.pragma.model.loan.valueObject.loan.LoanTerm;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;

public record Loan(
        LoanId id,
        LoanAmount amount,
        LoanTerm term,
        LoanUserDocument userDocument,
        StateId stateId,
        LoanTypeId loanTypeId
) {
}