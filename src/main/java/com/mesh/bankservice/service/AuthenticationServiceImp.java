package com.mesh.bankservice.service;

import java.util.Optional;

import com.mesh.bankservice.model.AuthenticateRequest;
import com.mesh.bankservice.model.AuthenticationResponse;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
import com.mesh.bankservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        User user = userService.findByEmail(request.getEmail()).orElseThrow();

        String jwtToken = jwtUtil.generateToken(user);

        return AuthenticationResponse.builder()
            .token(jwtToken)

            .build();
    }
}
