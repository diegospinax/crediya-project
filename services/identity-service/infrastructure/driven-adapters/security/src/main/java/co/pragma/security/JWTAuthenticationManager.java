package co.pragma.security;

import co.pragma.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenService tokenService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> tokenService.getClaims(auth.getCredentials().toString()))
                .log()
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.get("sub"),
                        null,
                        Stream.of(claims.get("role"))
                                .map(role -> new SimpleGrantedAuthority(role.asArray(String.class)[0]))
                                .toList()
                ));
    }
}
