package bck_rag_chat.authentication.service;

import bck_rag_chat.Jwt.JwtTokenProvider;
import bck_rag_chat.authentication.controller.request.LoginRequest;
import bck_rag_chat.authentication.controller.request.RegistrationRequest;
import bck_rag_chat.authentication.controller.response.LoginResponse;
import bck_rag_chat.authentication.controller.response.RegistrationResponse;
import bck_rag_chat.authentication.controller.response.RegistrationResponseBuilder;
import bck_rag_chat.authentication.service.exception.LoginException;
import bck_rag_chat.user.repository.UserRepository;
import bck_rag_chat.user.service.UserService;
import bck_rag_chat.user.service.dto.UserDTO;
import bck_rag_chat.user.service.dto.UserDTOBuilder;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationValidationService authenticationValidationService;

    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationValidationService authenticationValidationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationValidationService = authenticationValidationService;
    }

    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        authenticationValidationService.validateCommonAuthorizationRequest(registrationRequest);
        authenticationValidationService.validateRegistrationRequest(registrationRequest);
        //TODO: Insert email verification process
        return Optional.of(registrationRequest)
                .map(this::getUserDtoFromRequest)
                .map(userService::create)
                .map(this::getRegistrationResponseFromUserDto)
                .orElseThrow(() -> new RuntimeException("User registration failed"));
    }

    private UserDTO getUserDtoFromRequest(RegistrationRequest registrationRequest) {
        String encryptedPassword = Optional.of(registrationRequest)
                .map(RegistrationRequest::password)
                .map(passwordEncoder::encode)
                .orElseThrow(() -> new IllegalArgumentException("There was an error encoding the password"));

        return new UserDTOBuilder()
                .withEmail(registrationRequest.email())
                .withPassword(encryptedPassword)
                .build();
    }

    private RegistrationResponse getRegistrationResponseFromUserDto(UserDTO userDTO) {
        String token = jwtTokenProvider.generateToken(userDTO.email());
        return new RegistrationResponseBuilder()
                .withJwtToken(token)
                .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
        authenticationValidationService.validateCommonAuthorizationRequest(loginRequest);
        UserDTO userDTO = Optional.of(loginRequest.email())
                .map(userService::loadByEmail)
                .orElseThrow(() -> new LoginException("User not found"));

        return authorizeUser(loginRequest, userDTO);
    }

    private LoginResponse authorizeUser(LoginRequest loginRequest, UserDTO userDTO) {
        verifyPassword(loginRequest, userDTO);
        return Optional.of(userDTO.email())
                .map(jwtTokenProvider::generateToken)
                .map(LoginResponse::new)
                .orElseThrow(() -> new LoginException("There was an error during token generation"));
    }

    private void verifyPassword(LoginRequest loginRequest, UserDTO userDTO) {
        if (!passwordEncoder.matches(loginRequest.password(), userDTO.password())) {
            throw new LoginException("Password is not correct");
        }
    }

}
