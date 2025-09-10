package co.pragma.security.service;

import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.security.userdetails.AuthDetails;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(AuthDetails authDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withSubject(authDetails.getEmail())
                    .withClaim("userId", authDetails.getUserId())
                    .withClaim("role", authDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList())
                    .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.of("-05:00")))
                    .withExpiresAt(LocalDateTime.now().plusSeconds(expiration).toInstant(ZoneOffset.of("-05:00")))
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new AuthValidationException("Error while generating token. ");
        }

    }

    public String getSubject(String token) {
        return verifyToken(token).getSubject();
    }

    public Map<String, Claim> getClaims (String token) {
        return verifyToken(token).getClaims();
    }

    private DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .build()
                    .verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            throw new BadCredentialsException("Error while verifying token. ");
        }
    }

}
