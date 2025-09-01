package co.pragma.model.loan;

import java.time.LocalDate;

public record User(
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
