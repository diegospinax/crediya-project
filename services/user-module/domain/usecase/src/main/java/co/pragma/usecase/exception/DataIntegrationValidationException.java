package co.pragma.usecase.exception;

public class DataIntegrationValidationException extends RuntimeException {
    public DataIntegrationValidationException(String message) {
        super(message);
    }
}
