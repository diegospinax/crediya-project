package co.pragma.usecase.user.support;

import co.pragma.model.user.ctx.AuthContext;
import reactor.core.publisher.Mono;

public class ContextHolder {

    public static Mono<AuthContext> getAuthContext() {
        return Mono.deferContextual(ctx ->
                Mono.justOrEmpty(ctx.getOrEmpty(AuthContext.class)));
    }

    public static Mono<Long> getUserId() {
        return getAuthContext().map(AuthContext::getUserId);
    }

    public static Mono<String> getEmail() {
        return getAuthContext().map(AuthContext::getEmail);
    }

    public static Mono<String> getRole() {
        return getAuthContext().map(AuthContext::getRole);
    }

    public static Mono<Boolean> hasAnyRole(String... roles) {
        return getAuthContext()
                .map(ctx -> ctx.hasAnyRole(roles))
                .defaultIfEmpty(false);
    }
}
