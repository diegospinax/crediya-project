package co.pragma.config;

import co.pragma.model.role.gateways.RoleRepository;
import co.pragma.model.user.gateways.UserRepository;
import co.pragma.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.pragma.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        @Bean
        public UserUseCase userUseCase(UserRepository userRepository, RoleRepository roleRepository) {
                return new UserUseCase(userRepository, roleRepository);
        }
}
