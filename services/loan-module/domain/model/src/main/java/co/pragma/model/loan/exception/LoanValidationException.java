package co.pragma.model.loan.exception;

public class LoanValidationException extends RuntimeException {
    public LoanValidationException(String message) {
        super(message);
    }
}
