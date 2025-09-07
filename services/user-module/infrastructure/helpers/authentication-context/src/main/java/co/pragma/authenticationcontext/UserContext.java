package co.pragma.authenticationcontext;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserContext {

    private Long userId;
    private String email;
    private String role;

    public boolean hasAnyRole(String... checkRoles) {
        for (String role : checkRoles) {
            if (this.role.startsWith(role)) {
                return true;
            }
        }
        return false;
    }
}
