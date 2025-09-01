package co.pragma.r2dbc.loanType;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("loan_types")
public class LoanTypeEntity {
    @Id
    private Long id;
    private String name;
    @Column("min_amount")
    private Integer minAmount;
    @Column("max_amount")
    private Integer maxAmount;
    @Column("interest_rate")
    private Float interestRate;
    @Column("automatic_validate")
    private Boolean autoValidate;
}
