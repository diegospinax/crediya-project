package co.pragma.model.user.gateways;

import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> createUser(User user);
    Flux<User> findAll();
    Mono<User> findById(Long userId);
    Mono<User> findByEmail(UserEmail email);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUser(Long userId);
    Mono<User> findByDocument(UserDocument document);
}
