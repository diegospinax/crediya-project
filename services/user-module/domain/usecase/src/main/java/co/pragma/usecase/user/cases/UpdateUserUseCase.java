package co.pragma.usecase.user.cases;

import co.pragma.model.user.User;
import co.pragma.model.user.dto.UserResponse;
import co.pragma.model.user.valueObject.UserId;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {
    Mono<UserResponse> updateUser(UserId userId, User userNewData);
}
