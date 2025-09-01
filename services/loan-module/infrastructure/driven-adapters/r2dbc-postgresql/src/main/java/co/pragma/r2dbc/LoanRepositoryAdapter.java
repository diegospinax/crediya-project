package co.pragma.r2dbc;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.gateways.LoanRepository;
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
    public Flux<Loan> findAll() {
        return loanRepository.findAll()
                .flatMap(adapterMapper::mapToDomain);
    }
}
