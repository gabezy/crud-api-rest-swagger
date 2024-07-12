package com.gabezy.projects.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//For Spring Security versions below 6.X, the class needs to extend the WebSecurityConfigurationAdapter
public class SecurityConfig {

    private final String[] OPENAPI_WHITELIST = {
            "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html",
            "/swagger-ui/**", "/swagger-resources/configuration/ui",
    };

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // configure the security of HTTP Requests
        http
                .csrf(AbstractHttpConfigurer::disable)
                // set the session management to be STATELESS
                // that means that won't be HTTP session and every HTTP request will be handled independently
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(OPENAPI_WHITELIST).permitAll() // Swagger URL
                        .requestMatchers(HttpMethod.POST,"/users").anonymous() // only unauthenticated user can post this endpoint
                        .anyRequest().authenticated()
                ).addFilterAfter(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
