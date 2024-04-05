package com.mesh.bankservice.service;

import com.mesh.bankservice.model.AuthenticateRequest;
import com.mesh.bankservice.model.AuthenticationResponse;
import com.mesh.bankservice.model.User;
import com.mesh.bankservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final AuthenticationManager authManager;
    private final UserService userService;

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        User user = userService.getByEmail(request.getEmail());

        String token = "test token";
        return AuthenticationResponse.builder()
            .token(token)
            .build();
    }
}
