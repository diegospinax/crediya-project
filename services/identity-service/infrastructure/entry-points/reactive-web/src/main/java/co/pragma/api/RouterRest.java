package co.pragma.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        String BASE_URL = "/auth";
        return route(POST(BASE_URL + "/login"), handler::login)
                .andRoute(POST(BASE_URL + "/register"), handler::register)
                .and(route(GET(BASE_URL + "/{id}"), handler::findById));
    }
}