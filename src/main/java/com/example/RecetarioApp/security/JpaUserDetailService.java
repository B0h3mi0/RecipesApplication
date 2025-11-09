package com.example.RecetarioApp.security;

import com.example.RecetarioApp.domain.entities.UserEntity;
import com.example.RecetarioApp.domain.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class JpaUserDetailService implements UserDetailsService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Obteniendo usuario por username: {}",username);
        UserEntity user = repository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("No se encuentra usuario: "+username));
        log.info("Usuario encontrado");

        List<GrantedAuthority> authorities = user.getRoles()
                .stream().map(r ->
                        new SimpleGrantedAuthority(r.getName())).
                collect(Collectors.toList());

        log.info("Cantidad de roles: {}",authorities.size());
        String pass= passwordEncoder.encode("1234");
        log.info(pass);

        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
                );
    }
}