package co.pragma.api.dto.user;

import java.time.LocalDate;

public record SaveUserDto(
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
