package com.leplei.pathigo;

import com.leplei.pathigo.controllers.AuthenticationController;
import com.leplei.pathigo.controllers.UserController;
import com.leplei.pathigo.filters.JwtAuthenticationFilter;
import com.leplei.pathigo.persistence.adapter.UserPersistenceAdapter;
import com.leplei.pathigo.persistence.repository.JpaUserRepository;
import com.leplei.pathigo.ports.PasswordHasher;
import com.leplei.pathigo.ports.TokenProvider;
import com.leplei.pathigo.ports.UserRepository;
import com.leplei.pathigo.security.JwtService;
import com.leplei.pathigo.services.AuthenticationService;
import com.leplei.pathigo.usecases.GetUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class DependenciesConfigurations {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access.expiration-ms}")
    private Long accessExpirationMs;

    @Value("${security.jwt.refresh.expiration-ms}")
    private Long refreshExpirationMs;

    @Bean
    public UserRepository userRepository(
            JpaUserRepository jpaUserRepository
    ) {
        return new UserPersistenceAdapter(jpaUserRepository);
    }

    @Bean
    public GetUser GetUser(
            UserRepository userRepository
    ) {
        return new GetUser(userRepository);
    }

    @Bean
    public JwtService jwtService() {
        return new JwtService(secretKey, accessExpirationMs, refreshExpirationMs);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationService authenticationService(
            GetUser getUser,
            PasswordHasher passwordHasher,
            TokenProvider tokenProvider
    ) {
        return new AuthenticationService(getUser, passwordHasher, tokenProvider);
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
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            GetUser getUser
    ) {
        return new JwtAuthenticationFilter(jwtService, getUser);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(
                List.of("http://localhost:3000")
        );
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );
        config.setAllowedHeaders(
                List.of("Content-Type", "X-CSRF-TOKEN")
        );
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
