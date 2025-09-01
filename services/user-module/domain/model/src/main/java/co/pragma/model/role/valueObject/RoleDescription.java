package co.pragma.model.role.valueObject;

import co.pragma.model.role.exception.RoleValidationException;
import reactor.core.publisher.Mono;

public class RoleDescription extends RoleField<String>{

    private RoleDescription(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[a-zA-Z0-9]+([ _-]|, )[a-zA-Z0-9]+(([ _-]|, )[a-zA-Z0-9]+)*\\.?$";

        if(value == null)
            return Mono.error(new RoleValidationException("Role description is required."));
        if(!value.matches(regex)) {
            return Mono.error(new RoleValidationException("Role description must only contain letters, numbers, comma and period."));
        }

        return Mono.empty();
    }

    public static Mono<RoleDescription> create(String value) {
        RoleDescription description = new RoleDescription(value);
        return description.validate()
                .thenReturn(description);
    }
}
