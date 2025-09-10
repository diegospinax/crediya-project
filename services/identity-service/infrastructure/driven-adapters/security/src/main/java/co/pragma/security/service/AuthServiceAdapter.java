package co.pragma.security.service;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.Token;
import co.pragma.model.auth.dto.AuthRequest;
import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.model.auth.gateways.AuthService;
import co.pragma.model.auth.valueObject.AuthPassword;
import co.pragma.security.mapper.AuthServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthServiceAdapter implements AuthService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceMapper serviceMapper;

    @Override
    public Mono<Token> authenticate(AuthRequest authRequest, Auth existingAuth) {
        return serviceMapper.mapToUserDetails(existingAuth)
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(authRequest.password().value, userDetails.getPassword())) {
                        return Token.create(tokenService.generateToken(userDetails));
                    } else {
                        return Mono.error(new AuthValidationException("Invalid credentials."));
                    }
                });
    }

    @Override
    public Mono<AuthPassword> encryptPassword(AuthPassword password) {
        return AuthPassword.create(passwordEncoder.encode(password.value));
    }
}
