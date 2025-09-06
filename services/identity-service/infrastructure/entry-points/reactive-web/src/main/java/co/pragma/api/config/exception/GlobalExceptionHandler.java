package co.pragma.api.config.exception;

import co.pragma.model.auth.exception.AuthValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@Order(-2)
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<Mono<ExceptionResponse>> handleInvalidParametersProvided(ServerWebInputException e, ServerWebExchange exchange) {
        log.info("Server web input exception, {}", e.getMessage(), e);
        String message = "Bad request";

        if (e.getReason() != null && e.getReason().contains("Validation failure")) {
            message = "Validation failure";
        }

        ExceptionResponse response = new ExceptionResponse(
                message,
                LocalDateTime.now() ,
                exchange.getRequest().getPath().value()
        );
        return new ResponseEntity<>(Mono.just(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthValidationException.class})
    public ResponseEntity<Mono<ExceptionResponse>> handleValidationException(RuntimeException e, ServerWebExchange exchange) {
        log.info("Client exception, {}", e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                LocalDateTime.now() ,
                exchange.getRequest().getPath().value()
        );
        return new ResponseEntity<>(Mono.just(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Mono<ExceptionResponse>> handleGenericException(RuntimeException e, ServerWebExchange exchange) {
        log.info("Generic exception, {}", e.getMessage(), e);
        ExceptionResponse response = new ExceptionResponse(
                "Something went wrong.",
                LocalDateTime.now() ,
                exchange.getRequest().getPath().value()
        );

        return new ResponseEntity<>(Mono.just(response), HttpStatus.BAD_REQUEST);
    }

}
