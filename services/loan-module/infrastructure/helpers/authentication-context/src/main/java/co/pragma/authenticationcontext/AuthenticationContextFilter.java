package co.pragma.authenticationcontext;

import co.pragma.model.loan.ctx.AuthContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
@Component
@Order(-1)
public class AuthenticationContextFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        String email = exchange.getRequest().getHeaders().getFirst("X-User-Sub");
        String role = exchange.getRequest().getHeaders().getFirst("X-User-Role");

        log.info("DATA HEADERS: " + userId + email + role);
        if (userId == null || email == null || role == null) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        AuthContext authContext = new AuthContext(Long.parseLong(userId), email, role);

        return chain.filter(exchange)
                .contextWrite(Context.of(AuthContext.class, authContext));
    }
}
