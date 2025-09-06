package co.pragma.api.config.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(
        String message,
        LocalDateTime timestamp,
        String path
) {
}
