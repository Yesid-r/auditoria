package com.example.auditoria.service;

import com.example.auditoria.models.UserEntity;
import com.example.auditoria.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " +  username + "No existe"));
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())))

                .collect(Collectors.toSet());

        System.out.println("authorities = " + authorities);
        System.out.println("userEntity.getPassword() = " + userEntity.getPassword());
        System.out.println("userEntity.getUsername() = " + userEntity.getUsername());
        System.out.println("userEntity.getRoles() = " + userEntity.getRoles());

        return new User(userEntity.getUsername(), userEntity.getPassword(), true, true, true,true, authorities);
    }
}