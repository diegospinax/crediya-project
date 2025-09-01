package co.pragma.consumer;

import co.pragma.model.loan.User;
import co.pragma.model.loan.gateways.UserClient;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements UserClient {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = "findUserByDocument")
    public Mono<User> findUserByDocument(LoanUserDocument loanUserDocument) {
        return Mono.defer(() -> client
                .get()
                .uri("/document/{document}", loanUserDocument.value)
                .retrieve()
                .bodyToMono(User.class));
    }
}
