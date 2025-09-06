package co.pragma.model.user.dto;

import co.pragma.model.role.valueObject.RoleName;
import co.pragma.model.user.User;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String name,
        String lastname,
        String document,
        LocalDate dateBirth,
        String address,
        String phoneNumber,
        String email,
        Double salary,
        String role
) {
    public UserResponse(User user, RoleName roleName) {
        this(
                user.id().value,
                user.name().value,
                user.lastname().value,
                user.document().value,
                user.dateBirth().value,
                user.address().value,
                user.phoneNumber().value,
                user.email().value,
                user.salary().value,
                roleName.value
        );
    }
}
