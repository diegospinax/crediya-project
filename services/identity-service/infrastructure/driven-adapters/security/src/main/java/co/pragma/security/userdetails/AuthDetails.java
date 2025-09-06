package co.pragma.security.userdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private Integer failedLoginAttempts;
    private Boolean isLocked;
    private Long userId;
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_".concat(role)));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
