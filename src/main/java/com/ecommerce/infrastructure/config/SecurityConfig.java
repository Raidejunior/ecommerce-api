package com.ecommerce.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(JwtFilter jwtFilter, JwtAccessDeniedHandler accessDeniedHandler, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless (sem sessão)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/usuarios/cadastrarAdmin").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/produtos/atualizar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/produtos/deletar").hasRole("ADMIN")
                        .requestMatchers("/pedidos/todos").hasRole("ADMIN")
                        .requestMatchers("/api/relatorios/top-compradores").hasRole("ADMIN")
                        .requestMatchers("/api/relatorios/ticket-medio").hasRole("ADMIN")
                        .requestMatchers("/api/relatorios/faturamento").hasRole("ADMIN")
                        .anyRequest().authenticated() // Todos os outros precisam de autenticação
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
