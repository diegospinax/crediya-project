package co.pragma.api;

import co.pragma.api.dto.RequestLoan;
import co.pragma.api.dto.ResponseLoan;
import co.pragma.api.mapper.LoanEntryMapper;
import co.pragma.model.loan.valueObject.PaginationData;
import co.pragma.model.loan.valueObject.loanType.LoanTypeId;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.usecase.loan.cases.CreateLoanUseCase;
import co.pragma.usecase.loan.cases.FindLoanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Handler {

    private final CreateLoanUseCase createUseCase;
    private final FindLoanUseCase findLoanUseCase;
    private final LoanEntryMapper entryMapper;

    public Mono<ServerResponse> createLoan(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestLoan.class)
                .flatMap(entryMapper::mapToDomain)
                .flatMap(createUseCase::createLoan)
                .flatMap(loan -> {
                    URI location = UriComponentsBuilder
                            .fromPath("/loans/{id}")
                            .buildAndExpand(loan.id().value)
                            .toUri();

                    return ServerResponse.created(location).build();
                });
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        int page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(0);
        int size = serverRequest.queryParam("size").map(Integer::parseInt).orElse(10);
        Mono<StateId> stateIdMono = serverRequest
                .queryParam("stateId")
                .map(Long::parseLong)
                .map(StateId::create).orElse(null);

        if (stateIdMono == null)
            return ServerResponse.ok()
                    .body(
                            findLoanUseCase.findAll(null, new PaginationData(page, size))
                                    .map(entryMapper::mapToResponse)
                            ,
                            ResponseLoan.class
                    );

        return ServerResponse.ok()
                .body(stateIdMono
                                .flatMapMany(stateId ->
                                        findLoanUseCase.findAll(stateId, new PaginationData(page, size))
                                                .map(entryMapper::mapToResponse)
                                ),
                        ResponseLoan.class
                );

    }
}
