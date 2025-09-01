package co.pragma.api.mapper.support;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "id", source = "id.value")
@Mapping(target = "amount", source = "amount.value")
@Mapping(target = "term", source = "term.value")
@Mapping(target = "userDocument", source = "userDocument.value")
@Mapping(target = "stateId", source = "stateId.value")
@Mapping(target = "loanTypeId", source = "loanTypeId.value")
public @interface LoanMapDomainToResponse {
}
