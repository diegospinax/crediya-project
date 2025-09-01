package co.pragma.r2dbc.loanType.mapper.support;

import co.pragma.model.loan.LoanType;
import co.pragma.r2dbc.loanType.LoanTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanTypeDomainToEntityMapper {

    @LoanTypeMapDomainToEntity
    LoanTypeEntity mapToEntity(LoanType loanType);
}
