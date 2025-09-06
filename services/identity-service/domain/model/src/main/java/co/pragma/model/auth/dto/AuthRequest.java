package co.pragma.model.auth.dto;

import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.model.auth.valueObject.AuthPassword;

public record AuthRequest(
        AuthEmail email,
        AuthPassword password
) {
}
