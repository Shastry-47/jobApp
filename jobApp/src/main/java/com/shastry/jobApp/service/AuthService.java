package com.shastry.jobApp.service;

import com.shastry.jobApp.dto.AuthResponse;
import com.shastry.jobApp.dto.LoginRequest;
import com.shastry.jobApp.dto.RegisterRequest;
import com.shastry.jobApp.jwt.JwtService;
import com.shastry.jobApp.model.User;
import com.shastry.jobApp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        userRepository.findByEmailAndRole(request.email(), request.role())
                .ifPresent(u -> { throw new RuntimeException("User already exists with this email and role"); });

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        userRepository.save(user);

        // 👇 Generate JWT here
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getRole(), user.getName());
    }


    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndRole(request.email(), request.role())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken, user.getRole(), user.getName());
    }
}

