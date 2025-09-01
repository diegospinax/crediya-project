package co.pragma.usecase.user.cases;

import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindUserUseCase {
    Flux<User> findAll();
    Mono<User> findById(UserId userId);
    Mono<User> findByEmail(UserEmail email);
}
