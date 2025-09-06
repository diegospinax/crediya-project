package co.pragma.model.user.gateways;

import co.pragma.model.user.User;
import co.pragma.model.user.dto.UserResponse;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> createUser(User user);
    Flux<User> findAll();
    Mono<User> findById(UserId userId);
    Mono<User> findByEmail(UserEmail email);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUser(UserId userId);
    Mono<User> findByDocument(UserDocument document);
}
