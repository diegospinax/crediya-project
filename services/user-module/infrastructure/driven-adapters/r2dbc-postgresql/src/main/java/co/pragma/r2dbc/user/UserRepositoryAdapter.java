package co.pragma.r2dbc.user;


import co.pragma.model.user.User;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.model.user.valueObject.UserEmail;
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
    private final UserAdapterMapper userAdapterMapper;

    @Override
    @Transactional
    public Mono<User> createUser(User user) {
        UserEntity userEntity = userAdapterMapper.mapToEntity(user);
        return userRepository.save(userEntity)
                .flatMap(userAdapterMapper::mapToDomain);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll()
                .flatMap(userAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<User> findById(Long userId) {
        return userRepository.findById(userId)
                .flatMap(userAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<User> findByEmail(UserEmail email) {
        return userRepository.findByEmail(email.value)
                .flatMap(userAdapterMapper::mapToDomain);
    }

    @Override
    @Transactional
    public Mono<User> updateUser(User user) {
        UserEntity userEntity = userAdapterMapper.mapToEntity(user);
        return userRepository.save(userEntity)
                .flatMap(userAdapterMapper::mapToDomain);
    }

    @Override
    public Mono<Void> deleteUser(Long userId) {
        return userRepository.deleteById(userId);
    }


}
