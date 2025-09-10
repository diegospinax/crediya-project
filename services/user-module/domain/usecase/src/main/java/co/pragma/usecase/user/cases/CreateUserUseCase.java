package co.pragma.usecase.user.cases;

import co.pragma.model.user.User;
import co.pragma.model.user.dto.UserResponse;
import reactor.core.publisher.Mono;

public interface CreateUserUseCase {
    Mono<UserResponse> createUser(User user);
}
