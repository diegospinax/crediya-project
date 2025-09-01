package co.pragma.r2dbc.loanType;

import co.pragma.model.loan.LoanType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LoanTypeR2DBCRepository extends R2dbcRepository<LoanTypeEntity, Long> {
}
