package com.gabezy.projects.api.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gabezy.projects.api.domain.dtos.TokenDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {

    private final String SECRET = "SECRET"; //@TODO: mudar segredo
    private final String ISSUER = "com.gabezy";
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(SECRET); // Setando o algoritmo para utilizar o HMAC256 com a segredo
    }

    // Método para gerar um token JWT para um usuário específico
    public String generateToken(UserDetails userDetails) {
            // Criando o JWT Token com as informações do usuário
             return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withSubject(userDetails.getUsername()) // Define o usuário (subject)
                    .withIssuedAt(new Date()) // Define a data de emissão para agora
                    .withExpiresAt(expireInstant()) //Define a data de expiração do token
                    .sign(algorithm); // Assina o token udando o algoritmo;
    }

    // Método para obter o username do token
    public String getSubject(String token) {
        return decodedJWT(token).getSubject();
    }


    public LocalDateTime getExpireAt(String token) {
        Instant expireInstant = decodedJWT(token).getExpiresAt().toInstant();
        return LocalDateTime.ofInstant(expireInstant, ZoneId.of("America/Sao_Paulo"));
    }
    private DecodedJWT decodedJWT(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido " + e.getMessage());
        }
    }

    private Instant expireInstant() {
        Long twoHoursToSeconds = Long.valueOf(2 * 60 * 60) ;
        return Instant.now().plusSeconds(twoHoursToSeconds);
    }
}
