package co.pragma.r2dbc;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("loans")
public class LoanEntity {
    @Id
    private Long id;
    private Integer amount;
    private LocalDate term;
    @Column("document")
    private String userDocument;
    @Column("state_id")
    private Long stateId;
    @Column("loan_type_id")
    private Long loanTypeId;
}
