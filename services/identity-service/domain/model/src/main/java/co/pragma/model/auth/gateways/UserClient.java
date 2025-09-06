package co.pragma.model.auth.gateways;

import co.pragma.model.auth.User;
import co.pragma.model.auth.valueObject.AuthUserId;
import reactor.core.publisher.Mono;

public interface UserClient {
    Mono<User> findUserById(AuthUserId userId);

}
