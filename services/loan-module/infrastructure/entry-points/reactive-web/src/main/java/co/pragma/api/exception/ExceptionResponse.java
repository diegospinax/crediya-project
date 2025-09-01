package co.pragma.api.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(
        String message,
        LocalDateTime timestamp,
        String path
) {
}
