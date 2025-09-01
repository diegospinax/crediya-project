package co.pragma.api.dto.user;

import co.pragma.api.dto.role.ResponseRoleDto;

import java.time.LocalDate;

public record ResponseUserDto(
        Long id,
        String name,
        String lastname,
        LocalDate dateBirth,
        String address,
        String phoneNumber,
        String email,
        Double salary,
        Long roleId
) {
}
