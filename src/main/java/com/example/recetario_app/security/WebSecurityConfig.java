package com.example.recetario_app.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.recetario_app.security.jwt.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        private final JwtAuthenticationFilter jwtFilter;

        public WebSecurityConfig(JwtAuthenticationFilter jwtFilter) {
                this.jwtFilter = jwtFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                // CSRF solo para web (correcto)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )

                // 🔐 AUTORIZACIÓN
                .authorizeHttpRequests(auth -> auth

                // 🔓 LOGIN JWT
                .requestMatchers("/api/auth/**").permitAll()

                // 🔐 API PROTEGIDA (ROLES, ETC)
                .requestMatchers("/api/**").authenticated()

                // 🌍 WEB
                .requestMatchers(
                        "/",
                        "/login",
                        "/logout",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                .anyRequest().authenticated()
                )


                // 🌐 LOGIN CLÁSICO (THYMELEAF)
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // 🌐 LOGOUT CLÁSICO
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )

                // 🔐 JWT SOLO PARA API
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                AuthenticationConfiguration config
        ) throws Exception {
                return config.getAuthenticationManager();
        }
}