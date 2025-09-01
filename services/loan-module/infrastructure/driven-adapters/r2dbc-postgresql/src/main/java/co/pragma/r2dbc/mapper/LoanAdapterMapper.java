package co.pragma.r2dbc.mapper;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.valueObject.loan.LoanAmount;
import co.pragma.model.loan.valueObject.loan.LoanId;
import co.pragma.model.loan.valueObject.loan.LoanTerm;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.r2dbc.LoanEntity;
import co.pragma.r2dbc.mapper.support.LoanDomainToEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoanAdapterMapper {

    private final LoanDomainToEntityMapper mapperToEntity;

    public LoanEntity mapToEntity(Loan loan) {
        return mapperToEntity.domainToEntity(loan);
    }

    public Mono<Loan> mapToDomain(LoanEntity entity) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                LoanId.create(entity.getId()),
                LoanAmount.create(entity.getAmount()),
                LoanTerm.create(entity.getTerm()),
                LoanUserDocument.create(entity.getUserDocument()),
                StateId.create(entity.getStateId()),
                LoanTypeId.create(entity.getLoanTypeId())
        );

        return Mono.zip(fieldsMono, fields -> new Loan(
                (LoanId) fields[0],
                (LoanAmount) fields[1],
                (LoanTerm) fields[2],
                (LoanUserDocument) fields[3],
                (StateId) fields[4],
                (LoanTypeId) fields[5]
        ));
    }
}
