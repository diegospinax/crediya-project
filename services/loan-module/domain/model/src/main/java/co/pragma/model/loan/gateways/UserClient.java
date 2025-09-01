package co.pragma.model.loan.gateways;

import co.pragma.model.loan.User;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import reactor.core.publisher.Mono;

public interface UserClient {
    Mono<User> findUserByDocument(LoanUserDocument loanUserDocument);
}
