package com.mesh.bankservice.service;

import com.mesh.bankservice.model.AuthenticateRequest;
import com.mesh.bankservice.model.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticateRequest request);
}
