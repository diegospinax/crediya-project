package co.pragma.model.role.valueObject;

import co.pragma.model.role.exception.RoleValidationException;
import reactor.core.publisher.Mono;

public class RoleName extends RoleField<String>{

    private RoleName(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[A-Za-zÁÉÍÓÚÑáéíóúñ]+(?:_[A-Za-zÁÉÍÓÚÑáéíóúñ]+)?$";
        if(value == null)
            return Mono.error(new RoleValidationException("Role name is required."));
        if(!value.matches(regex))
            return Mono.error(new RoleValidationException("Role must contain only letters and underscore."));

        this.value = this.value.toUpperCase();

        return Mono.empty();
    }

    public static Mono<RoleName> create(String value) {
        RoleName name = new RoleName(value);
        return name.validate()
                .thenReturn(name);
    }
}
