package com.leplei.pathigo;

import com.leplei.pathigo.controllers.AuthenticationController;
import com.leplei.pathigo.controllers.UserController;
import com.leplei.pathigo.persistence.adapter.UserPersistenceAdapter;
import com.leplei.pathigo.persistence.repository.JpaUserRepository;
import com.leplei.pathigo.ports.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DependenciesConfigurations {

    @Bean
    public UserRepository userRepository(
            JpaUserRepository jpaUserRepository
    ) {
        return new UserPersistenceAdapter(jpaUserRepository);
    }

    @Bean
    public AuthenticationController authenticationController(
            UserRepository userRepository
    ) {
        return new AuthenticationController(userRepository);
    }

    @Bean
    public UserController userController(
            UserRepository userRepository
    ) {
        return new UserController(userRepository);
    }
}
