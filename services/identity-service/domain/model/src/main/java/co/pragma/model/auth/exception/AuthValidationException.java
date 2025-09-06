package co.pragma.model.auth.exception;

public class AuthValidationException extends RuntimeException{
    public AuthValidationException(String message) {
        super(message);
    }
}
