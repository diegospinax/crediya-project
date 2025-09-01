package co.pragma.r2dbc.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("users")
@Data
public class UserEntity {
    @Id
    private Long id;
    private String name;
    private String lastname;
    @Column("date_birth")
    private LocalDate dateBirth;
    private String address;
    @Column("phone_number")
    private String phoneNumber;
    private String email;
    private Double salary;
    @Column("id_role")
    private Long roleId;

}
