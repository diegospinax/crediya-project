package co.pragma.consumer;

import co.pragma.model.auth.User;
import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.model.auth.gateways.UserClient;
import co.pragma.model.auth.valueObject.AuthUserId;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserClient {

    private final WebClient client;

    @Override
    @CircuitBreaker(name = "findUserById")
    public Mono<User> findUserById(AuthUserId userId) {
        return Mono.defer(() -> client
                .get()
                .uri("/{id}", userId.value)
                .retrieve()
                .bodyToMono(User.class))
                .onErrorResume(e -> {
                    log.error("Error ocurred while fetching user: ", e);
                    return Mono.error(new AuthValidationException(String.format("User with id %d does not exists.", userId.value)));
                });
    }
}
