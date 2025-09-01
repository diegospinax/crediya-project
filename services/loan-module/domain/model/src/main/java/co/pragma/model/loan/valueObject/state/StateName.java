package co.pragma.model.loan.valueObject.state;

import co.pragma.model.loan.exception.StateValidationException;
import reactor.core.publisher.Mono;

public class StateName extends StateField<String>{
    public StateName(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^\\p{L}+(?:_\\p{L}+)*$";
        if(value == null) {
            return Mono.error(new StateValidationException("State name is required."));
        }
        if (value.length() < 3 ){
            return Mono.error(new StateValidationException("State name min length are 3 characters."));
        }
        if (!value.matches(regex)) {
            return Mono.error(new StateValidationException("State name must contain only letters an underscore."));
        }

        return Mono.empty();
    }

    public static Mono<StateName> create(String value) {
        StateName name = new StateName(value);
        return name.validate()
                .thenReturn(name);
    }
}
