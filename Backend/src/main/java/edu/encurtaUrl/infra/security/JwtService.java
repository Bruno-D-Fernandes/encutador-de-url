package edu.encurtaUrl.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    // Aprender a como usar env com spring
    private String SECRET = "UHNFNYD";
    private Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String createToken(String email){
        Instant expiresAt = OffsetDateTime.now().plusHours(2).withOffsetSameLocal(ZoneOffset.of("-03:00")).toInstant();

        String tokenJwt = JWT.create()
                .withExpiresAt(expiresAt)
                .withSubject(email)
                .withIssuer("BackEndEncurta")
                .sign(algorithm);

        return tokenJwt;
    }

    public String decodeSubjectToken(String token){
        Instant expiresAt = OffsetDateTime.now().plusHours(2).withOffsetSameLocal(ZoneOffset.of("-03:00")).toInstant();

        try{
            String subject = JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

            return subject;
        }catch (JWTVerificationException jwtVerificationException){
            return "";
        }
    }


}
