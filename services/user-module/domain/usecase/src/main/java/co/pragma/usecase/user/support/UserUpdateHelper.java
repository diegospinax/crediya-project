package co.pragma.usecase.user.support;

import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.UserField;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import reactor.core.publisher.Mono;

public class UserUpdateHelper {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserUpdateHelper(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Mono<User> validateKeysChanges (User userUpdate, User currentUser) {
        return Mono.just(userUpdate)
                .flatMap(nextUser -> validateEmailChange(nextUser, currentUser))
                .flatMap(this::validateRolChange);
    }

    public User updateFields (User updateUser, User currentUser) {
        return new User(
                currentUser.id(),
                updateUserField(updateUser.name(), currentUser.name()),
                updateUserField(updateUser.lastname(), currentUser.lastname()),
                updateUserField(updateUser.dateBirth(), currentUser.dateBirth()),
                updateUserField(updateUser.address(), currentUser.address()),
                updateUserField(updateUser.phoneNumber(), currentUser.phoneNumber()),
                updateUserField(updateUser.email(), currentUser.email()),
                updateUserField(updateUser.salary(), currentUser.salary()),
                updateUser.roleId() != null ? updateUser.roleId() : currentUser.roleId()
        );
    }

    private Mono<User> validateEmailChange(User userUpdate, User currentUser) {
        if (userUpdate.email() == null || currentUser.email().value.equals(userUpdate.email().value)) {
            return Mono.just(userUpdate);
        }
        return userRepository.findByEmail(userUpdate.email())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new DataIntegrationValidationException("Email already registered."));
                    }
                    return Mono.just(userUpdate);
                });
    }

    private Mono<User> validateRolChange(User userUpdate) {
        if (userUpdate.roleId() == null) {
            return Mono.just(userUpdate);
        }
        return roleRepository.findById(userUpdate.roleId())
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role does not exists.")))
                .map(role -> userUpdate);
    }

    private <T extends UserField<K>, K> T updateUserField(T newValue, T userCurrentFieldValue) {
        return newValue != null ? newValue : userCurrentFieldValue;
    }
}
