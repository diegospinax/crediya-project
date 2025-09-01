package co.pragma.model.loan.exception;

public class LoanTypeValidationException extends RuntimeException {
    public LoanTypeValidationException(String message) {
        super(message);
    }
}
