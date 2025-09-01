package co.pragma.usecase.loan.cases;

import co.pragma.model.loan.Loan;
import reactor.core.publisher.Flux;

public interface FindLoanUseCase {
    Flux<Loan> findAll();
}
