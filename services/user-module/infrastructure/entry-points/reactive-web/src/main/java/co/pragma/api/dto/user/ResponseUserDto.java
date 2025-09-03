package co.pragma.api.dto.user;

import java.time.LocalDate;

public record ResponseUserDto(
        Long id,
        String name,
        String lastname,
        String document,
        LocalDate dateBirth,
        String address,
        String phoneNumber,
        String email,
        Double salary,
        Long roleId
) {
}
