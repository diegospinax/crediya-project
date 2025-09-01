package co.pragma.r2dbc.state;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("states")
public class StateEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
