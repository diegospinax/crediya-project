package co.pragma.r2dbc.state;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("states")
public class StateEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
