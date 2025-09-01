package co.pragma.model.loan.exception;

public class StateValidationException extends RuntimeException {
    public StateValidationException(String message) {
        super(message);
    }
}
