package co.pragma.api.dto;

import java.time.LocalDate;

public record RequestLoan(
        Integer amount,
        LocalDate term,
        String userDocument,
        Long loanTypeId
) {
}
