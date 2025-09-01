package co.pragma.r2dbc.mapper.support;

import co.pragma.model.loan.Loan;
import co.pragma.r2dbc.LoanEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDomainToEntityMapper {

    @LoanMapDomainToEntity
    LoanEntity domainToEntity(Loan loan);
}
