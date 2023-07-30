package com.example.auditoria.controller;

import com.example.auditoria.controller.request.CreateUserDTO;
import com.example.auditoria.models.ERole;
import com.example.auditoria.models.RoleEntity;
import com.example.auditoria.models.UserEntity;
import com.example.auditoria.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String hello(){
        return "Hello world not secured";
    }
    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hello world secured";
    }

    @PostMapping("/createUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream().map(role -> RoleEntity.builder().name(ERole.valueOf(role)).build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))// Encriptacion de la contraseña
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();


        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);

    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Se ha borrado el user con id: ".concat(id);

    }


}