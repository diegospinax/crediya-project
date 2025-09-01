package co.pragma.api.dto;

import java.time.LocalDate;

public record ResponseLoan(
        Long id,
        Integer amount,
        LocalDate term,
        String userDocument,
        Long stateId,
        Long loanTypeId
) {
}
