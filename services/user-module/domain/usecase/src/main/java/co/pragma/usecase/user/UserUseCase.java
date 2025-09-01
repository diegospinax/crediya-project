package co.pragma.usecase.user;

import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import co.pragma.usecase.user.cases.CreateUserUseCase;
import co.pragma.usecase.user.cases.DeleteUserUseCase;
import co.pragma.usecase.user.cases.FindUserUseCase;
import co.pragma.usecase.user.cases.UpdateUserUseCase;
import co.pragma.usecase.user.support.UserRoleValidation;
import co.pragma.usecase.user.support.UserUpdateHelper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UserUseCase implements CreateUserUseCase, FindUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Mono<User> createUser(User user) {
        UserRoleValidation roleValidation = new UserRoleValidation(roleRepository);

        return userRepository.findByEmail(user.email())
                .flatMap(existing -> Mono.error(new DataIntegrationValidationException("Email already registered.")))
                .then(Mono.just(user))
                .flatMap(userNext -> roleValidation.validateRoleExists(user))
                .flatMap(userRepository::createUser);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(UserId userId) {
        return userRepository.findById(userId.value)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")));
    }

    @Override
    public Mono<User> findByEmail(UserEmail email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")));
    }

    @Override
    public Mono<User> updateUser(UserId userId, User userNewData) {
        UserUpdateHelper updateHelper = new UserUpdateHelper(userRepository, roleRepository);

        return userRepository.findById(userId.value)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(existingUser -> updateHelper.validateKeysChanges(userNewData, existingUser))
                .map(existingUser -> updateHelper.updateFields(existingUser, userNewData))
                .flatMap(userRepository::updateUser);
    }

    @Override
    public Mono<Void> deleteUser(UserId userId) {
        return userRepository.findById(userId.value)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(user -> userRepository.deleteUser(user.id().value));
    }
}
