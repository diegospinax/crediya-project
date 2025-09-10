package co.pragma.r2dbc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("auth")
public class AuthEntity {
    @Id
    private Long id;
    private String email;
    private String password;
    @Column("failed_login_attempts")
    private Integer failedLoginAttempts;
    @Column("is_locked")
    private Boolean isLocked;
    @Column("user_id")
    private Long userId;
}
