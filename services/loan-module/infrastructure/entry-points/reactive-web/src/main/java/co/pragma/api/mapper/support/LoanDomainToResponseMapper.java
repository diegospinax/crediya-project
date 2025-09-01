package co.pragma.api.mapper.support;

import co.pragma.api.dto.ResponseLoan;
import co.pragma.model.loan.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDomainToResponseMapper {

    @LoanMapDomainToResponse
    ResponseLoan mapToResponse(Loan loan);
}
