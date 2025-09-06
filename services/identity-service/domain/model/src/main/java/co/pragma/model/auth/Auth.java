package co.pragma.model.auth;


import co.pragma.model.auth.valueObject.*;
import reactor.core.publisher.Mono;

public record Auth(
        AuthId id,
        AuthEmail email,
        AuthPassword password,
        AuthFailedAttempts failedLoginAttempts,
        AuthIsLocked isLocked,
        AuthUserId userId,
        AuthRole role
) {
    public Auth withRole(AuthRole authRole){
        return new Auth(
                this.id(),
                this.email(),
                this.password(),
                this.failedLoginAttempts(),
                this.isLocked(),
                this.userId(),
                authRole
        );
    }

    public Auth withPassword(AuthPassword authPassword) {
        return new Auth(
                this.id(),
                this.email(),
                authPassword,
                this.failedLoginAttempts(),
                this.isLocked(),
                this.userId(),
                this.role());
    }
}
