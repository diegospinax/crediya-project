package co.pragma.r2dbc;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.gateways.AuthRepository;
import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.r2dbc.mapper.AuthAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryAdapter implements AuthRepository {

    private final AuthR2DBCRepository authRepository;
    private final AuthAdapterMapper adapterMapper;


    @Override
    public Mono<Auth> register(Auth auth) {
        AuthEntity authEntity = adapterMapper.mapToEntity(auth);
        return authRepository.save(authEntity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<Auth> findByEmail(AuthEmail email) {
        return authRepository.findByEmail(email.value)
                .flatMap(adapterMapper::mapToDomain);
    }
}
