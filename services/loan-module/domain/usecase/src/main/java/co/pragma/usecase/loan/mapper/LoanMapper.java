package co.pragma.usecase.loan.mapper;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.valueObject.state.StateId;
import reactor.core.publisher.Mono;

public class LoanMapper {

    public Mono<Loan> creationMap(Loan loan, StateId stateId) {
        return Mono.just(new Loan(
                null,
                loan.amount(),
                loan.term(),
                loan.userDocument(),
                stateId,
                loan.loanTypeId()
        ));
    }
}
