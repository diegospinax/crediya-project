package co.pragma.api;

import co.pragma.api.dto.RequestLoan;
import co.pragma.api.dto.ResponseLoan;
import co.pragma.api.mapper.LoanEntryMapper;
import co.pragma.usecase.loan.cases.CreateLoanUseCase;
import co.pragma.usecase.loan.cases.FindLoanUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final CreateLoanUseCase createUseCase;
    private final FindLoanUseCase findLoanUseCase;
    private final LoanEntryMapper entryMapper;

    @PostMapping("/create")
    public ResponseEntity<Mono<ResponseLoan>> createLoan(@RequestBody RequestLoan requestLoan) {
        return new ResponseEntity<>(
                entryMapper.mapToDomain(requestLoan)
                        .flatMap(createUseCase::createLoan)
                        .map(entryMapper::mapToResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Flux<ResponseLoan>> findAll() {
        return new ResponseEntity<>(
                findLoanUseCase.findAll()
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

}
