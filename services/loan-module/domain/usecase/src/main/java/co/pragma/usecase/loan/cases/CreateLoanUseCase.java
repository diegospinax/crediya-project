package co.pragma.usecase.loan.cases;

import co.pragma.model.loan.Loan;
import reactor.core.publisher.Mono;

public interface CreateLoanUseCase {
    Mono<Loan> createLoan(Loan loan);
}
