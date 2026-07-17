package com.leplei.pathigo;

import com.leplei.pathigo.controllers.AuthenticationController;
import com.leplei.pathigo.controllers.UserController;
import com.leplei.pathigo.filters.JwtAuthenticationFilter;
import com.leplei.pathigo.persistence.adapter.UserPersistenceAdapter;
import com.leplei.pathigo.persistence.repository.JpaUserRepository;
import com.leplei.pathigo.ports.PasswordHasher;
import com.leplei.pathigo.ports.TokenProvider;
import com.leplei.pathigo.ports.UserRepository;
import com.leplei.pathigo.security.CustomUserDetailsService;
import com.leplei.pathigo.security.JwtService;
import com.leplei.pathigo.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class DependenciesConfigurations {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-ms}")
    private Long expirationMs;

    @Bean
    public UserRepository userRepository(
            JpaUserRepository jpaUserRepository
    ) {
        return new UserPersistenceAdapter(jpaUserRepository);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(secretKey, expirationMs);
    }

    @Bean
    public AuthenticationService authenticationService(
            UserRepository userRepository,
            PasswordHasher passwordHasher,
            TokenProvider tokenProvider
    ) {
        return new AuthenticationService(userRepository, passwordHasher, tokenProvider);
    }

    @Bean
    public AuthenticationController authenticationController(
            AuthenticationService authenticationService
    ) {
        return new AuthenticationController(authenticationService);
    }

    @Bean
    public UserController userController(
            UserRepository userRepository
    ) {
        return new UserController(userRepository);
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(
            UserRepository userRepository
    ) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService
    ) {
        return new JwtAuthenticationFilter(jwtService, customUserDetailsService);
    }
}
