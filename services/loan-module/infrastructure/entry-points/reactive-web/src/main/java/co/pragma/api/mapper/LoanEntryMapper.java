package co.pragma.api.mapper;

import co.pragma.api.dto.RequestLoan;
import co.pragma.api.dto.ResponseLoan;
import co.pragma.api.mapper.support.LoanDomainToResponseMapper;
import co.pragma.model.loan.Loan;
import co.pragma.model.loan.valueObject.loan.LoanAmount;
import co.pragma.model.loan.valueObject.loan.LoanTerm;
import co.pragma.model.loan.valueObject.loan.LoanUserDocument;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class LoanEntryMapper {

    private final LoanDomainToResponseMapper mapperToResponse;

    public LoanEntryMapper(LoanDomainToResponseMapper mapperToResponse) {
        this.mapperToResponse = mapperToResponse;
    }

    public Mono<Loan> mapToDomain(RequestLoan requestLoan) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                LoanAmount.create(requestLoan.amount()),
                LoanTerm.create(requestLoan.term()),
                LoanUserDocument.create(requestLoan.userDocument()),
                StateId.create(requestLoan.stateId()),
                LoanTypeId.create(requestLoan.loanTypeId())
        );

        return Mono.zip(fieldsMono, fields -> new Loan(
                null,
                (LoanAmount) fields[0],
                (LoanTerm) fields[1],
                (LoanUserDocument) fields[2],
                (StateId) fields[3],
                (LoanTypeId) fields[4]
        ));
    }

    public ResponseLoan mapToResponse(Loan loan) {
        return mapperToResponse.mapToResponse(loan);
    }
}
