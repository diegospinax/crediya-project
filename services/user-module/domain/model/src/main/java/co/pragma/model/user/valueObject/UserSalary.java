package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserSalary extends UserField<Double>{

    private UserSalary(Double value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if(value == null)
            return Mono.error(new UserValidationException("Salary is required."));
        if(value < 0d || value > 15_000_000d)
            return Mono.error(new UserValidationException("Invalid salary provided."));

        return Mono.empty();
    }

    public static Mono<UserSalary> create(Double value) {
        UserSalary salary = new UserSalary(value);
        return salary.validate()
                .thenReturn(salary);
    }
}
