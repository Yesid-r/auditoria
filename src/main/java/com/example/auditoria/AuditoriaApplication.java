package com.example.auditoria;

import com.example.auditoria.models.ERole;
import com.example.auditoria.models.RoleEntity;
import com.example.auditoria.models.UserEntity;
import com.example.auditoria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class AuditoriaApplication {
    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuditoriaApplication.class, args);
    }
    @Bean
    CommandLineRunner inir(){
        return args -> {
            UserEntity userEntity1 = UserEntity.builder()
                    .email("dairo3rincon@gmail.com")
                    .username("dairo")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .name(ERole.valueOf(ERole.ADMIN.name()))
                            .build()))
                    .build();
            UserEntity userEntity2 = UserEntity.builder()
                    .email("yesid3rincon@gmail.com")
                    .username("yesid")
                    .password(passwordEncoder.encode("1234"))
                    .roles(Set.of(RoleEntity.builder()
                            .name(ERole.valueOf(ERole.USER.name()))
                            .build()))
                    .build();

            userRepository.save(userEntity1);
            userRepository.save(userEntity2);

        };
    }
}
