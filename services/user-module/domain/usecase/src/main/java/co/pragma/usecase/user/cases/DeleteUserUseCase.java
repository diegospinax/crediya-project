package co.pragma.usecase.user.cases;

import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Mono;

public interface DeleteUserUseCase {
    Mono<Void> deleteUser(UserId userId);
}
