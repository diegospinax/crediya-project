package co.pragma.model.loan.gateways;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.valueObject.Page;
import co.pragma.model.loan.valueObject.PaginationData;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository {
    Mono<Loan> createLoan(Loan loan);
    Flux<Loan> findAll(StateId stateId, PaginationData paginationData);
}
