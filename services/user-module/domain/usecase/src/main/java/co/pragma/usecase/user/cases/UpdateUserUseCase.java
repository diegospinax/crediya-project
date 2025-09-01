package co.pragma.usecase.user.cases;

import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {
    Mono<User> updateUser(UserId userId, User userNewData);
}
