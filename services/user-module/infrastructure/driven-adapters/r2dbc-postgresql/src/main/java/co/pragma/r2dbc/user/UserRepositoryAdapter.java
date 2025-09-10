package co.pragma.r2dbc.user;


import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import co.pragma.r2dbc.user.mapper.UserAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserR2DBCRepository userRepository;
    private final UserAdapterMapper adapterMapper;

    @Override
    @Transactional
    public Mono<User> createUser(User user) {
        UserEntity userEntity = adapterMapper.mapToEntity(user);
        return userRepository.save(userEntity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll()
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<User> findById(UserId userId) {
        return userRepository.findById(userId.value)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<User> findByEmail(UserEmail email) {
        return userRepository.findByEmail(email.value)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    @Transactional
    public Mono<User> updateUser(User user) {
        UserEntity userEntity = adapterMapper.mapToEntity(user);
        return userRepository.save(userEntity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<Void> deleteUser(UserId userId) {
        return userRepository.deleteById(userId.value);
    }

    @Override
    public Mono<User> findByDocument(UserDocument document) {
        return userRepository.findByDocument(document.value)
                .flatMap(adapterMapper::mapToDomain);
    }


}
