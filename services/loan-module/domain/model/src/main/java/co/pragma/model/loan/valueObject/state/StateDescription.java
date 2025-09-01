package co.pragma.model.loan.valueObject.state;

import co.pragma.model.loan.exception.StateValidationException;
import reactor.core.publisher.Mono;

public class StateDescription extends StateField<String>{
    public StateDescription(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[a-zA-Z0-9]+([ _-]|, )[a-zA-Z0-9]+(([ _-]|, )[a-zA-Z0-9]+)*\\.?$";
        if (value == null) {
            return Mono.error(new StateValidationException("State description is required."));
        }
        if (value.length() < 3){
            return Mono.error(new StateValidationException("State description min length are 3 characters."));
        }
        if (!value.matches(regex)) {
            return Mono.error(new StateValidationException("State description must contain only letters, numbers, comma and period."));
        }

        return Mono.empty();
    }

    public static Mono<StateDescription> create(String value) {
        StateDescription description = new StateDescription(value);
        return description.validate()
                .thenReturn(description);
    }
}
