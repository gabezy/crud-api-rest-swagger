package com.gabezy.projects.api.config.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gabezy.projects.api.domain.entity.User;
import com.gabezy.projects.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository; // TODO: Verificar se usar o UserService é melhor

    private final String TOKEN_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = retrieveToken(request);
            if (Objects.nonNull(token)) {
                String subject = this.tokenService.getSubject(token);
                User user = this.userRepository.findByUsername(subject)
                        .orElseThrow(() -> new ServletException("User not found"));

                // Cria o token de autenticação e configura o contexto de segurança
                var authenticationToken = new UsernamePasswordAuthenticationToken(user, token, null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            // TODO: implementar condição para verificar o algum usuário está acessando alguma URL protegida
            // TODO: Criar exceção detalhada
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String retrieveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }
        return token.replace(TOKEN_PREFIX, "");
    }
}