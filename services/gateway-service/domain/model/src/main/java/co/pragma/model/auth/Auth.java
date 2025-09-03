package co.pragma.model.auth;


import co.pragma.model.auth.valueObject.*;

public record Auth(
        AuthId id,
        AuthEmail email,
        AuthPassword password,
        AuthFailedAttempts failedLoginAttempts,
        AuthIsLocked isLocked,
        AuthUserId userId,
        AuthRole role
) {


}
