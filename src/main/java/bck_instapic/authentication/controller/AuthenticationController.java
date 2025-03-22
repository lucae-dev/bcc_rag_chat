package bck_instapic.authentication.controller;

import bck_instapic.authentication.controller.request.LoginRequest;
import bck_instapic.authentication.controller.request.RegistrationRequest;
import bck_instapic.authentication.controller.response.LoginResponse;
import bck_instapic.authentication.controller.response.RegistrationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {
    private final AuthenticationDelegate authenticationDelegate;

    public AuthenticationController(AuthenticationDelegate authenticationDelegate) {
        this.authenticationDelegate = authenticationDelegate;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authenticationDelegate.register(registrationRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationDelegate.login(loginRequest));
    }
}
