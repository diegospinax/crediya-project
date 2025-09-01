package co.pragma.r2dbc.loanType;

import co.pragma.model.loan.LoanType;
import co.pragma.model.loan.gateways.LoanTypeRepository;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.r2dbc.loanType.mapper.LoanTypeAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LoanTypeRepositoryAdapter implements LoanTypeRepository {

    private final LoanTypeAdapterMapper adapterMapper;
    private final LoanTypeR2DBCRepository loanTypeRepository;

    @Override
    public Mono<LoanType> createLoanType(LoanType loanType) {
        LoanTypeEntity entity = adapterMapper.mapToEntity(loanType);
        return loanTypeRepository.save(entity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Flux<LoanType> findAll() {
        return loanTypeRepository.findAll()
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<LoanType> findById(LoanTypeId loanTypeId) {
        return loanTypeRepository.findById(loanTypeId.value)
                .flatMap(adapterMapper::mapToDomain);
    }
}
