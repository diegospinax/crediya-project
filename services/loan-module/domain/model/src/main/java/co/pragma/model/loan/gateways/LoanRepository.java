package co.pragma.model.loan.gateways;

import co.pragma.model.loan.Loan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository {
    Mono<Loan> createLoan(Loan loan);
    Flux<Loan> findAll();
}
