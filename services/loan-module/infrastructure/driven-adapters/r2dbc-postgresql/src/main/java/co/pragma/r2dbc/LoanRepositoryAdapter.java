package co.pragma.r2dbc;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.gateways.LoanRepository;
import co.pragma.model.loan.valueObject.PaginationData;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.r2dbc.mapper.LoanAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanRepositoryAdapter implements LoanRepository {

    private final LoanR2DBCRepository loanRepository;
    private final LoanAdapterMapper adapterMapper;

    @Override
    public Mono<Loan> createLoan(Loan loan) {
        LoanEntity loanEntity = adapterMapper.mapToEntity(loan);
        return loanRepository.save(loanEntity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Flux<Loan> findAll(StateId stateId, PaginationData paginationData) {
        int offset = paginationData.pageSize() *  paginationData.pageNumber();

        if (stateId != null)
            return loanRepository.findAllPaginatedByStateId(stateId.value, paginationData.pageSize(), offset)
                    .flatMap(adapterMapper::mapToDomain);
        else
            return loanRepository.findAllPaginated(paginationData.pageSize(), offset)
                    .flatMap(adapterMapper::mapToDomain);
    }
}
