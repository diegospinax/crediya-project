package co.pragma.usecase.user.cases;

import co.pragma.model.user.dto.UserResponse;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindUserUseCase {
    Flux<UserResponse> findAll();
    Mono<UserResponse> findById(UserId userId);
    Mono<UserResponse> findByEmail(UserEmail email);
    Mono<UserResponse> findByDocument(UserDocument document);
}
