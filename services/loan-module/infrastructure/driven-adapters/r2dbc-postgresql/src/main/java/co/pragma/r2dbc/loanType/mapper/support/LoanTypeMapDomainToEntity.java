package co.pragma.r2dbc.loanType.mapper.support;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "id.value")
@Mapping(target = "name", source = "name.value")
@Mapping(target = "minAmount", source = "minAmount.value")
@Mapping(target = "maxAmount", source = "maxAmount.value")
@Mapping(target = "interestRate", source = "interestRate.value")
@Mapping(target = "autoValidate", source = "autoValidate.value")
public @interface LoanTypeMapDomainToEntity {
}
