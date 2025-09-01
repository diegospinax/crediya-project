package co.pragma.r2dbc.loanType.mapper;

import co.pragma.model.loan.LoanType;
import co.pragma.model.loan.valueObject.loanType.*;
import co.pragma.r2dbc.loanType.LoanTypeEntity;
import co.pragma.r2dbc.loanType.mapper.support.LoanTypeDomainToEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanTypeAdapterMapper {

    private final LoanTypeDomainToEntityMapper mapperToEntity;

    public LoanTypeEntity mapToEntity(LoanType loanType) {
        return mapperToEntity.mapToEntity(loanType);
    }

    public Mono<LoanType> mapToDomain(LoanTypeEntity entity){
        List<Mono<?>> fieldsMono = Arrays.asList(
                LoanTypeId.create(entity.getId()),
                LoanTypeName.create(entity.getName()),
                LoanTypeMinAmount.create(entity.getMinAmount()),
                LoanTypeMaxAmount.create(entity.getMaxAmount()),
                LoanTypeInterestRate.create(entity.getInterestRate()),
                LoanTypeAutoValidate.create(entity.getAutoValidate())
        );

        return Mono.zip(fieldsMono, fields -> new LoanType (
                (LoanTypeId) fields[0],
                (LoanTypeName) fields[1],
                (LoanTypeMinAmount) fields[2],
                (LoanTypeMaxAmount) fields[3],
                (LoanTypeInterestRate) fields[4],
                (LoanTypeAutoValidate) fields[5]
        ));
    }

}
