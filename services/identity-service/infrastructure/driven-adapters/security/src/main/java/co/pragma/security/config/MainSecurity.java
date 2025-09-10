package co.pragma.security.config;

import co.pragma.security.JWTFilter;
import co.pragma.security.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class MainSecurity {

    private final SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JWTFilter filter) {
        return http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/login")
                        .permitAll()
                        .anyExchange()
                        .hasRole("ADMIN")
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(formLogin -> formLogin.disable())
                .addFilterAfter(filter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .build();
    }
}
