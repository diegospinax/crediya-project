package co.pragma.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@Order(-2)
public class SecurityExceptionHandler implements WebExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(SecurityExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof AuthenticationException) {
            return handleAuthenticationException(exchange, (AuthenticationException) ex);
        } else if (ex instanceof AccessDeniedException) {
            return handleAccessDeniedException(exchange, (AccessDeniedException) ex);
        } else if (ex instanceof JWTDecodeException) {
            return handleJwtException(exchange, (JWTDecodeException) ex);
        }

        return Mono.error(ex);
    }

    private Mono<Void> handleAuthenticationException(ServerWebExchange exchange,
                                                     AuthenticationException ex) {
        logger.error("Authentication error: {}", ex.getMessage());

        return buildErrorResponse(exchange, HttpStatus.UNAUTHORIZED,
                "Authentication error", ex.getMessage());
    }

    private Mono<Void> handleAccessDeniedException(ServerWebExchange exchange,
                                                   AccessDeniedException ex) {
        logger.error("Access denied: {}", ex.getMessage());

        return buildErrorResponse(exchange, HttpStatus.FORBIDDEN,
                "Access denied", "Insufficient privileges");
    }

    private Mono<Void> handleJwtException(ServerWebExchange exchange, JWTDecodeException ex) {
        logger.error("JWT error: {}", ex.getMessage());

        return buildErrorResponse(exchange, HttpStatus.UNAUTHORIZED,
                "Invalid token", "Invalid or expired token");
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange,
                                          HttpStatus status,
                                          String errorCode,
                                          String message) {
        ServerHttpResponse response = exchange.getResponse();

        if (response.isCommitted()) {
            return Mono.error(new IllegalStateException("Response already committed"));
        }

        response.setStatusCode(status);
        response.getHeaders().set("Content-Type", "application/json");

        String errorBody = """
            {
                "error": "%s",
                "message": "%s",
                "timestamp": "%s",
                "path": "%s"
            }
            """.formatted(
                errorCode,
                message,
                Instant.now(),
                exchange.getRequest().getPath().value()
        );

        DataBuffer buffer = response.bufferFactory().wrap(errorBody.getBytes());
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> logger.error("Error writing response", error));
    }
}
