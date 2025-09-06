package co.pragma.model.auth.gateways;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.Token;
import co.pragma.model.auth.dto.AuthRequest;
import co.pragma.model.auth.valueObject.AuthPassword;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<Token> authenticate(AuthRequest authRequest, Auth existingAuth);
    Mono<AuthPassword> encryptPassword(AuthPassword password);
}
