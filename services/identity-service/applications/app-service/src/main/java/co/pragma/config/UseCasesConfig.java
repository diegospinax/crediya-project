package co.pragma.config;

import co.pragma.model.auth.gateways.AuthRepository;
import co.pragma.model.auth.gateways.AuthService;
import co.pragma.model.auth.gateways.UserClient;
import co.pragma.usecase.AuthUseCase;
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
        public AuthUseCase authUseCase(AuthRepository authRepository, UserClient userClient, AuthService authService) {
                return new AuthUseCase(authRepository, userClient, authService);
        }
}
