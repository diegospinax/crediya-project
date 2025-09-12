package co.pragma.usecase.loan.cases;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.valueObject.PaginationData;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindLoanUseCase {
    Flux<Loan> findAll(StateId stateId, PaginationData paginationData);
}
