package co.pragma.api;

import co.pragma.api.dto.user.ResponseUserDto;
import co.pragma.api.dto.user.SaveUserDto;
import co.pragma.api.mapper.user.UserEntryMapper;
import co.pragma.model.user.User;
import co.pragma.model.user.valueObject.UserDocument;
import co.pragma.model.user.valueObject.UserEmail;
import co.pragma.model.user.valueObject.UserId;
import co.pragma.usecase.user.cases.CreateUserUseCase;
import co.pragma.usecase.user.cases.DeleteUserUseCase;
import co.pragma.usecase.user.cases.FindUserUseCase;
import co.pragma.usecase.user.cases.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserEntryMapper entryMapper;
    private final CreateUserUseCase createUseCase;
    private final FindUserUseCase findUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/create")
    public ResponseEntity<Mono<ResponseUserDto>> createUser(@RequestBody SaveUserDto saveUserDto) {
        return new ResponseEntity<>(
                entryMapper.mapToDomain(saveUserDto)
                        .flatMap(createUseCase::createUser)
                        .map(entryMapper::mapToResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ResponseUserDto>> findById(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(
                UserId.create(userId)
                        .flatMap(findUserUseCase::findById)
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/document")
    public ResponseEntity<Mono<ResponseUserDto>> findByDocument(@RequestParam("value") String document) {
        return new ResponseEntity<>(
                UserDocument.create(document)
                        .flatMap(findUserUseCase::findByDocument)
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/email")
    public ResponseEntity<Mono<ResponseUserDto>> findByEmail(@RequestParam("value") String email) {
        return new ResponseEntity<>(
                UserEmail.create(email)
                        .flatMap(findUserUseCase::findByEmail)
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Flux<ResponseUserDto>> findAll() {
        return new ResponseEntity<>(
                findUserUseCase.findAll()
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<ResponseUserDto>> updateUser(@PathVariable("id") Long userId, @RequestBody SaveUserDto saveUserDto) {
        Mono<UserId> userIdMono = UserId.create(userId);
        return new ResponseEntity<>(
                Mono
                        .zip(userIdMono, entryMapper.mapToDomain(saveUserDto))
                        .flatMap(parameters -> {
                            UserId id = parameters.getT1();
                            User user = parameters.getT2();
                            return updateUserUseCase.updateUser(id, user);
                        })
                        .map(entryMapper::mapToResponse),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteUser(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(
                UserId.create(userId)
                                .flatMap(deleteUserUseCase::deleteUser),
                HttpStatus.NO_CONTENT
        );
    }
}
