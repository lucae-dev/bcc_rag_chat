package bck_instapic.authentication.service;

import bck_instapic.authentication.controller.request.AuthenticationRequest;
import bck_instapic.authentication.service.exception.RegistrationException;
import bck_instapic.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthenticationValidationService {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private final UserRepository userRepository;

    public AuthenticationValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public <T extends AuthenticationRequest> void validateCommonAuthorizationRequest(T registrationRequest) {
        validateAuthorizationRequestFields(registrationRequest);
    }

    public <T extends AuthenticationRequest> void validateRegistrationRequest(T registrationRequest) {
        Optional.of(registrationRequest)
                .map(T::email)
                .map(userRepository::findByEmail)
                .ifPresent(__ -> {
                    throw new RegistrationException("Email already registered!");
                });
    }

    private static <T extends AuthenticationRequest> void validateAuthorizationRequestFields(T request) {
        if (request == null) {
            throw new RegistrationException("RegistrationRequest cannot be null");
        }

        validateRequestEmail(request.email());
        validateRequestPassword(request.password());
    }

    private static void validateRequestPassword(String password) {
        // Password requirements: at least 8 characters, one uppercase, one lowercase, one number, and one special character
        // String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (password == null || password.isBlank() || password.length() < 6) {
            throw new RegistrationException("Your password should have at least 6 characters");
        }
    }

    private static void validateRequestEmail(String email) {
        if (email.length() < 4 ) {
            throw new RegistrationException("Username must have at least 4 characters");
        } else if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new RegistrationException("Email not valid");
        }
    }
}
