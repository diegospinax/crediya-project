package co.pragma.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface LoanR2DBCRepository extends R2dbcRepository<LoanEntity, Long> {

    @Query("SELECT * FROM loans LIMIT :pageSize OFFSET :offset")
    Flux<LoanEntity> findAllPaginated(@Param("pageSize") int pageSize, @Param("offset") int offset);

    @Query("SELECT * FROM loans WHERE loans.state_id = :stateId LIMIT :pageSize OFFSET :offset")
    Flux<LoanEntity> findAllPaginatedByStateId(Long stateId, int i, int offset);
}
