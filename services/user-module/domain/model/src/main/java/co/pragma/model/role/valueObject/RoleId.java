package co.pragma.model.role.valueObject;

import co.pragma.model.role.exception.RoleValidationException;
import reactor.core.publisher.Mono;

public class RoleId extends RoleField<Long>{

    private RoleId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if(value == null) {
            return Mono.error(new RoleValidationException("Role Id is required."));
        }
        return Mono.empty();
    }

    public static Mono<RoleId> create(Long value) {
        RoleId roleId = new RoleId(value);
        return roleId.validate()
                .thenReturn(roleId);
    }
}
