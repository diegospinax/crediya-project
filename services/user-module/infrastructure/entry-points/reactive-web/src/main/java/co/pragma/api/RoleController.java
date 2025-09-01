package co.pragma.api;

import co.pragma.api.dto.role.ResponseRoleDto;
import co.pragma.api.dto.role.SaveRoleDto;
import co.pragma.api.mapper.role.RoleEntryMapper;
import co.pragma.api.mapper.role.support.RoleDomainToResponseMapper;
import co.pragma.model.role.Role;
import co.pragma.usecase.role.cases.CreateRoleUseCase;
import co.pragma.usecase.role.cases.FindRoleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final FindRoleUseCase findRoleUseCase;
    private final RoleEntryMapper roleMapper;

    @PostMapping("/create")
    public ResponseEntity<Mono<ResponseRoleDto>> createRole(@RequestBody SaveRoleDto saveRoleDto) {
        return new ResponseEntity<>(
                roleMapper.mapToDomain(saveRoleDto)
                        .flatMap(createRoleUseCase::createRole)
                        .map(roleMapper::mapToResponse),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/list")
    public ResponseEntity<Flux<ResponseRoleDto>> findAll() {
        return new ResponseEntity<>(
                findRoleUseCase.findAll()
                        .map(roleMapper::mapToResponse),
                HttpStatus.OK
        );
    }
}
