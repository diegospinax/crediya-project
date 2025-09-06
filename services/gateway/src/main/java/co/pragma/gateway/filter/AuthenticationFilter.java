package co.pragma.gateway.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Value("${jwt.secret}")
    private String secret;

    private final SecurityProperties securityProperties;

    public AuthenticationFilter(SecurityProperties securityProperties) {
        super(Config.class);
        this.securityProperties = securityProperties;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String path = request.getURI().getPath();
            System.out.println("Path: " + path);

            if (isPublicRoute(path))
                return chain.filter(exchange);

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "No authorization header.", HttpStatus.UNAUTHORIZED);

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer "))
                return onError(exchange, "Invalid authorization header.", HttpStatus.UNAUTHORIZED);

            String token = authHeader.replace("Bearer ", "");

            try {

                Map<String, Claim> claims = verifyToken(token).getClaims();

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-User-Id", claims.get("userId").asString())
                        .header("X-User-Sub", claims.get("sub").asString())
                        .header("X-User-Role", claims.get("role").asList(String.class).getFirst())
                        .build();

                exchange.getAttributes().put("claims", claims);

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                return onError(exchange, "Invalid token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }));
    }

    private boolean isPublicRoute(String route) {
        return securityProperties.getPublicRoutes().stream()
                .anyMatch(route::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        byte[] messageBytes = ("{\"error\": \"" + message + "\"}")
                .getBytes(StandardCharsets.UTF_8);

        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(messageBytes)));
    }

    private DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.require(algorithm)
                .build()
                .verify(token);
    }

    public static class Config {
    }
}
