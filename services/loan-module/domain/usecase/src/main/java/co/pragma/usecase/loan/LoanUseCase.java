package co.pragma.usecase.loan;

import co.pragma.model.loan.Loan;
import co.pragma.model.loan.State;
import co.pragma.model.loan.gateways.LoanRepository;
import co.pragma.model.loan.gateways.LoanTypeRepository;
import co.pragma.model.loan.gateways.StateRepository;
import co.pragma.model.loan.gateways.UserClient;
import co.pragma.model.loan.valueObject.state.StateName;
import co.pragma.usecase.exceptions.NotFoundException;
import co.pragma.usecase.loan.cases.CreateLoanUseCase;
import co.pragma.usecase.loan.cases.FindLoanUseCase;
import co.pragma.usecase.loan.mapper.LoanMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class LoanUseCase implements CreateLoanUseCase, FindLoanUseCase {

    private final LoanTypeRepository loanTypeRepository;
    private final StateRepository stateRepository;
    private final LoanRepository loanRepository;
    private final UserClient userClient;
    private final LoanMapper loanMapper;

    public LoanUseCase(LoanTypeRepository loanTypeRepository, StateRepository stateRepository, LoanRepository loanRepository, UserClient userClient) {
        this.loanTypeRepository = loanTypeRepository;
        this.stateRepository = stateRepository;
        this.loanRepository = loanRepository;
        this.userClient = userClient;
        this.loanMapper = new LoanMapper();
    }

    @Override
    public Mono<Loan> createLoan(Loan loan) {
        return userClient.findUserByDocument(loan.userDocument())
                .switchIfEmpty(Mono.error(new NotFoundException("User not found.")))
                .then(loanTypeRepository.findById(loan.loanTypeId())
                        .switchIfEmpty(Mono.error(new NotFoundException("Loan type does not exists."))))
                .then(StateName.create("PENDING_REVIEW")
                        .flatMap(stateRepository::findByName)
                        .switchIfEmpty(Mono.error(new NotFoundException("Default state does not exist.")))
                        .map(State::id))
                .flatMap(stateId -> loanMapper.creationMap(loan, stateId))
                .flatMap(loanRepository::createLoan);
    }

    @Override
    public Flux<Loan> findAll() {
        return loanRepository.findAll();
    }
}
