package co.pragma.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LoanR2DBCRepository extends R2dbcRepository<LoanEntity, Long> {
}
