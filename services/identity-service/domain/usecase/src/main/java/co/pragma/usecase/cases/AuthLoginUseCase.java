package co.pragma.usecase.cases;

import co.pragma.model.auth.valueObject.Token;
import co.pragma.model.auth.dto.AuthRequest;
import reactor.core.publisher.Mono;

public interface AuthLoginUseCase {
    Mono<Token> login(AuthRequest authRequest);
}
