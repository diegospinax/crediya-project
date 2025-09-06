package co.pragma.usecase.user;

import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.User;
import co.pragma.model.user.dto.UserResponse;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import co.pragma.usecase.exception.DataIntegrationValidationException;
import co.pragma.usecase.user.cases.CreateUserUseCase;
import co.pragma.usecase.user.cases.DeleteUserUseCase;
import co.pragma.usecase.user.cases.FindUserUseCase;
import co.pragma.usecase.user.cases.UpdateUserUseCase;
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
    public Mono<UserResponse> createUser(User user) {
        return userRepository.findByEmail(user.email())
                .flatMap(existing -> Mono.error(new DataIntegrationValidationException("Email already registered.")))
                .then(userRepository.findByDocument(user.document()))
                .flatMap(existing -> Mono.error(new DataIntegrationValidationException("Document already registered.")))
                .then(roleRepository.findById(user.roleId())
                        .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role does not exists."))))
                .then(userRepository.createUser(user))
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Flux<UserResponse> findAll() {
        return userRepository.findAll()
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Mono<UserResponse> findById(UserId userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Mono<UserResponse> findByEmail(UserEmail email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Mono<UserResponse> findByDocument(UserDocument document) {
        return userRepository.findByDocument(document)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Mono<UserResponse> updateUser(UserId userId, User userNewData) {
        UserUpdateHelper updateHelper = new UserUpdateHelper(userRepository, roleRepository);

        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(existingUser -> updateHelper.validateKeysChanges(userNewData, existingUser))
                .map(existingUser -> updateHelper.updateFields(existingUser, userNewData))
                .flatMap(userRepository::updateUser)
                .flatMap(this::mapUserToResponse);
    }

    @Override
    public Mono<Void> deleteUser(UserId userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("User does not exists.")))
                .flatMap(user -> userRepository.deleteUser(user.id()));
    }

    private Mono<UserResponse> mapUserToResponse(User user) {
        return roleRepository.findById(user.roleId())
                .switchIfEmpty(Mono.error(new DataIntegrationValidationException("Role does not exists.")))
                .map(role -> new UserResponse(user, role.name()));
    }
}
