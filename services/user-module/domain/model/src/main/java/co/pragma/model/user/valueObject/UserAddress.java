package co.pragma.model.user.valueObject;

import co.pragma.model.user.exception.UserValidationException;
import reactor.core.publisher.Mono;

public class UserAddress extends UserField<String> {

    private UserAddress(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String cityAndCountryNameRegex = "\\p{L}+(?:_\\p{L}+)*";
        String streetNumberRegex = "#\\d{2}-\\d{2}";
        String streetNameRegex = "[\\p{L}\\d]+(?:_[\\p{L}\\d]+)?";

        String regex = "^" + streetNameRegex + "_" + streetNumberRegex + "\\._" + cityAndCountryNameRegex +  ",_" + cityAndCountryNameRegex + "$";

        if(value == null)
            return Mono.error(new UserValidationException("Address is required."));

        if (!value.matches(regex)) {
            return Mono.error(new UserValidationException("Invalid address provided."));
        }

        this.value = value.toUpperCase();

        return Mono.empty();
    }

    public static Mono<UserAddress> create(String value) {
        UserAddress address = new UserAddress(value);
        return address.validate()
                .thenReturn(address);
    }
}
