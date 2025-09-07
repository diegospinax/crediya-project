package co.pragma.authenticationcontext;

import reactor.core.publisher.Mono;

public class ContextHolder {

    public static Mono<UserContext> getUserContext() {
        return Mono.deferContextual(ctx ->
                Mono.justOrEmpty(ctx.getOrEmpty(UserContext.class)));
    }

    public static Mono<Long> getUserId() {
        return getUserContext().map(UserContext::getUserId);
    }

    public static Mono<String> getEmail() {
        return getUserContext().map(UserContext::getEmail);
    }

    public static Mono<String> getRole() {
        return getUserContext().map(UserContext::getRole);
    }

    public static Mono<Boolean> hasAnyRole(String... roles) {
        return getUserContext()
                .map(ctx -> ctx.hasAnyRole(roles))
                .defaultIfEmpty(false);
    }
}
