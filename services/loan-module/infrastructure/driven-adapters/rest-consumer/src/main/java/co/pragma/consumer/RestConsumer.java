package co.pragma.consumer;

import co.pragma.model.loan.User;
import co.pragma.model.loan.exception.UserClientException;
import co.pragma.model.loan.gateways.UserClient;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestConsumer implements UserClient {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = "findUserByDocument")
    public Mono<User> findUserByDocument(LoanUserDocument loanUserDocument) {
        return Mono.defer(() -> client
                .get()
                .uri(url -> url
                        .path("/document")
                        .queryParam("value", loanUserDocument.value)
                        .build())
                .retrieve()
                .bodyToMono(User.class))
                .onErrorResume(e -> {
                    log.error("Error ocurred while fetching user: ", e);
                    return Mono.error(new UserClientException(String.format("User with document %s does not exists.", loanUserDocument.value)));
                });
    }
}
