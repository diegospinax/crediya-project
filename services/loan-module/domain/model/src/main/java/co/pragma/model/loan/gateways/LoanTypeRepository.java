package co.pragma.model.loan.gateways;

import co.pragma.model.loan.LoanType;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> createLoanType(LoanType loanType);
    Flux<LoanType> findAll();
    Mono<LoanType> findById(LoanTypeId loanTypeId);
}
