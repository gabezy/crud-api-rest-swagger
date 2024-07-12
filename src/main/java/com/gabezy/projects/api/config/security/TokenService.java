package com.gabezy.projects.api.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido " + e.getMessage());
        }
    }

    private Instant expireInstant() {
        Long twoHoursToSeconds = Long.valueOf(2 * 60 * 60) ;
        return Instant.now().plusSeconds(twoHoursToSeconds);
    }
}
