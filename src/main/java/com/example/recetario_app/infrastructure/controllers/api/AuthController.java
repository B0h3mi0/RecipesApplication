package com.example.recetario_app.infrastructure.controllers.api;

import com.example.recetario_app.infrastructure.dtos.auth.JwtResponse;
import com.example.recetario_app.infrastructure.dtos.auth.LoginRequest;
import com.example.recetario_app.security.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;

        @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(
                @Valid @RequestBody LoginRequest request
        ) {
                // 🔐 Autenticación con Spring Security
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

                // 👤 Usuario autenticado
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                // 🎟️ Generar JWT
                String token = jwtService.generateToken(userDetails);

                return ResponseEntity.ok(new JwtResponse(token));
        }
}
