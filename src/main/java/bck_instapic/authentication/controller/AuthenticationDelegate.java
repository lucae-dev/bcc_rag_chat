package bck_instapic.authentication.controller;

import bck_instapic.authentication.controller.request.LoginRequest;
import bck_instapic.authentication.controller.request.RegistrationRequest;
import bck_instapic.authentication.controller.response.LoginResponse;
import bck_instapic.authentication.controller.response.RegistrationResponse;
import bck_instapic.authentication.service.AuthenticationService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDelegate {
    private final AuthenticationService authenticationService;

    public AuthenticationDelegate(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        return authenticationService.register(registrationRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }
}
