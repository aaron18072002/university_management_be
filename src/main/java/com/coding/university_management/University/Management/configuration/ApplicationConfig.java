package com.coding.university_management.University.Management.configuration;

import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.enums.Role;
import com.coding.university_management.University.Management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());

                User user = new User().builder()
                        .username("admin")
                        .password(this.passwordEncoder.encode("12345678"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.info(user.toString());
                log.warn("admin user created with default password, please change it");
            }
        };
    }

}
